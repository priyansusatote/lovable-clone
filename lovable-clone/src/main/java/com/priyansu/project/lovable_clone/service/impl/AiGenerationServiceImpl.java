package com.priyansu.project.lovable_clone.service.impl;

import com.priyansu.project.lovable_clone.llm.PromptUtils;
import com.priyansu.project.lovable_clone.llm.advisors.FileTreeContextAdvisor;
import com.priyansu.project.lovable_clone.llm.tools.CodeGenerationTools;
import com.priyansu.project.lovable_clone.security.AuthUtil;
import com.priyansu.project.lovable_clone.service.AiGenerationService;
import com.priyansu.project.lovable_clone.service.ProjectFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.Map;
import java.util.Objects;
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

    private static final Pattern FILE_TAG_PATTERN = Pattern.compile("<file path=\"([^\"]+)\">(.*?)</file>", Pattern.DOTALL);

    @Override
    @PreAuthorize("@securityExpression.canEditProject(#projectId)")
    public Flux<String> streamResponse(String userPrompt, Long projectId) {
        Long userId = authUtil.getCurrentUserId();

        createChatSessionIfNotExists(projectId, userId); //createChatSession for user if not created

        Map<String, Object> advisorParams = Map.of(  //some advisor params we are passing along with LLM call to the advisor request
                "userId", userId,
                "projectId", projectId);

        StringBuilder fullResponseBuffer = new StringBuilder();

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
                    fullResponseBuffer.append(content);  //keep-on adding chunks to sb
                })
                .doOnComplete(() -> {  //once we get all content(we buffered in sb(fullResponseBuffer) so get full), then we Parse content & do some operations on it
                    Schedulers.boundedElastic().schedule(() -> { //by this line you are calling this method in completely diff Thread
                        parseAndSaveFiles(fullResponseBuffer.toString(), projectId);
                    });    //boundedElastic -> puts a bounded(hard limit) limits on no.of Threads your application can use
                })
                .doOnError(error -> log.error("Error During Streaming for projectId: {}", projectId))
                .map(response -> Objects.requireNonNull(response.getResult().getOutput().getText()));  //for each response(ChatResponse) we got From LLM ,Extract Text from it(// Take the text from each LLM response chunk and send it as a plain String to the client.)

    }

    //Parsing = convert one format into another structured form.
    //LLM text with <file ...> tags  → extract path + content using regex
    private void parseAndSaveFiles(String fullResponse, Long projectId) {
//        String dummy = """
//                <message> I'm going to read the files and generate the code </message>
//                <file path ="src/App.jsx">
//                    import App from './App.jsx'
//                    ......
//                </file>
//                 <message> I'm going to read the files and generate the code </message>
//                <file path ="src/App.jsx">
//                    import App from './App.jsx'
//                    ......
//                </file>
//                """

        Matcher matcher = FILE_TAG_PATTERN.matcher(fullResponse); //it takes fullResponse(ex given dummy String above)

        while (matcher.find()) {  //will keep-on finding "FILE_TAG_PATTERN" Regular expr. (<file path> </file> in matcher)
            String filePath = matcher.group(1);
            String fileContent = matcher.group(2).trim();

            projectFileService.saveFile(projectId ,filePath, fileContent);

        }
    }

    private void createChatSessionIfNotExists(Long projectId, Long userId) {

    }
}
