package com.priyansu.project.lovable_clone.service.impl;

import com.priyansu.project.lovable_clone.dto.chat.StreamResponse;
import com.priyansu.project.lovable_clone.entity.*;
import com.priyansu.project.lovable_clone.enums.ChatEventType;
import com.priyansu.project.lovable_clone.enums.MessageRole;
import com.priyansu.project.lovable_clone.exception.ResourceNotFoundException;
import com.priyansu.project.lovable_clone.llm.PromptUtils;
import com.priyansu.project.lovable_clone.llm.advisors.FileTreeContextAdvisor;
import com.priyansu.project.lovable_clone.llm.tools.CodeGenerationTools;
import com.priyansu.project.lovable_clone.llm.tools.LlmResponseParser;
import com.priyansu.project.lovable_clone.repository.*;
import com.priyansu.project.lovable_clone.security.AuthUtil;
import com.priyansu.project.lovable_clone.service.AiGenerationService;
import com.priyansu.project.lovable_clone.service.ProjectFileService;
import com.priyansu.project.lovable_clone.service.UsageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiGenerationServiceImpl implements AiGenerationService {

    private final ChatClient chatClient;
    private final AuthUtil authUtil;
    private final ProjectFileService projectFileService;
    private final FileTreeContextAdvisor fileTreeContextAdvisor;
    private final ChatSessionRepository chatSessionRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final LlmResponseParser llmResponseParser;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatEventRepository chatEventRepository;
    private final UsageService usageService;


    private static final Pattern FILE_TAG_PATTERN = Pattern.compile("<file path=\"([^\"]+)\">(.*?)</file>", Pattern.DOTALL);

    @Override
    @PreAuthorize("@securityExpression.canEditProject(#projectId)")
    public Flux<StreamResponse> streamResponse(String userPrompt, Long projectId) {

        usageService.checkDailyTokensUsage();

        Long userId = authUtil.getCurrentUserId();

        ChatSession chatSession = createChatSessionIfNotExists(projectId, userId); //createChatSession for user if not created

        Map<String, Object> advisorParams = Map.of(  //some advisor params we are passing along with LLM call to the advisor request
                "userId", userId,
                "projectId", projectId);

        StringBuilder fullResponseBuffer = new StringBuilder();

        AtomicReference<Long> startTime = new AtomicReference<>(System.currentTimeMillis());
        AtomicReference<Long> endTime = new AtomicReference<>(0L);  //atomic reference is for get "endTime" from which thread it called
        AtomicReference<Usage> usageRef = new AtomicReference<>();
        CodeGenerationTools codeGenerationTools = new CodeGenerationTools(projectFileService, projectId);

        return chatClient.prompt()
                .system(PromptUtils.CODE_GENERATION_SYSTEM_PROMPT) //can also Pass FileTree by just: +projectFileService.getFileTree(projectId) , but following convection
                .user(userPrompt)
                .tools(codeGenerationTools)  //our tool to readFile content
                .advisors(advisorSpec -> {
                            advisorSpec.params(advisorParams);
                            advisorSpec.advisors(fileTreeContextAdvisor); //our custom Advisor (to pass FileTree) we can do it without custom Advisor by just passing fileTree With SystemPrompt (but following best Practices)
                        }
                )
                .stream()
                .chatResponse()
                .doOnNext(response -> {
                    String content = response.getResult().getOutput().getText();  //getting Chuck
                    if (content != null && !content.isEmpty() && endTime.get() == 0) {
                        endTime.set(System.currentTimeMillis());
                    }
                    if(response.getMetadata().getUsage() != null){
                        usageRef.set(response.getMetadata().getUsage());
                    }
                    fullResponseBuffer.append(content); //keep-on adding chunks to sb

                })
                .doOnComplete(() -> {  //once we get all content(we buffered in sb(fullResponseBuffer) so get full), then we Parse content & do some operations on it
                    Schedulers.boundedElastic().schedule(() -> { //by this line you are calling this method in completely diff Thread
                        Long duration = (endTime.get() - startTime.get()) / 1000;
                        try {
                            finalizeChats(userPrompt, chatSession, fullResponseBuffer.toString(), duration, usageRef.get());
                        } catch (Exception e) {
                            log.error("Finalize failed", e);
                        }
                    });    //boundedElastic -> puts a bounded(hard limit) limits on no.of Threads your application can use
                })
                .doOnError(error -> log.error("Error During Streaming for projectId: {}", projectId))
                .map(response -> {
                    String text = response.getResult().getOutput().getText();
                    return new StreamResponse(text != null ? text : "");
                });
    }

    //save all the Events
    private void finalizeChats(String userMessage, ChatSession chatSession, String fullText, Long duration, Usage usage) {
        Long projectId = chatSession.getProject().getId();

        Long userId = chatSession.getUser().getId();
        if(usage != null){
            int totalTokens = usage.getTotalTokens();
            usageService.recordTokenUsage(userId, totalTokens);
        }

        //store the userPrompt(userMessage)
        chatMessageRepository.save(
                ChatMessage.builder()
                        .chatSession(chatSession)
                        .role(MessageRole.USER)
                        .content(userMessage)
                        .tokenUsed(usage.getPromptTokens())
                        .build()
        );

        //save the LLM chat message
        ChatMessage assistantChatMessage = ChatMessage.builder()
                .role(MessageRole.ASSISTANT)
                .chatSession(chatSession)
                .content("Assistant Message here...")
                .tokenUsed(usage.getCompletionTokens())
                .build();
        assistantChatMessage = chatMessageRepository.save(assistantChatMessage);

        List<ChatEvent> chatEventsList = llmResponseParser.parseChatEvents(fullText, assistantChatMessage);
        chatEventsList.addFirst(ChatEvent.builder()
                .type(ChatEventType.THOUGHT)
                .chatMessage(assistantChatMessage)
                .content("Thought for " + duration + " s")
                .sequenceOrder(0)
                .build());

        //store the file
        chatEventsList.stream()
                .filter(e -> e.getType() == ChatEventType.FILE_EDIT)
                .forEach(e -> projectFileService.saveFile(projectId, e.getFilePath(), e.getContent()));

        //save all the Chat Events
        chatEventRepository.saveAll(chatEventsList);

    }


    //Note: ChatSession will be created only Once for everyUser for one Project
    private ChatSession createChatSessionIfNotExists(Long projectId, Long userId) {
        ChatSessionId chatSessionId = new ChatSessionId(projectId, userId);

        ChatSession chatSession = chatSessionRepository.findById(chatSessionId).orElse(null);

        if (chatSession == null) { //create new chatSession using user and project
            Project project = projectRepository.findById(projectId)
                    .orElseThrow(() -> new ResourceNotFoundException(("Project"), projectId.toString()));

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException(("User"), userId.toString()));

            chatSession = ChatSession.builder()
                    .id(chatSessionId)
                    .project(project)
                    .user(user)
                    .build();
            chatSessionRepository.save(chatSession);
        }

        return chatSession;
    }
}
