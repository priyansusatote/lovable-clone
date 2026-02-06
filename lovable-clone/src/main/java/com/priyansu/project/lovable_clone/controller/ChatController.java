package com.priyansu.project.lovable_clone.controller;

import com.priyansu.project.lovable_clone.dto.chat.ChatRequest;
import com.priyansu.project.lovable_clone.dto.chat.ChatResponse;
import com.priyansu.project.lovable_clone.dto.chat.StreamResponse;
import com.priyansu.project.lovable_clone.service.AiGenerationService;
import com.priyansu.project.lovable_clone.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {

    private final AiGenerationService aiGenerationService;
    private final ChatService chatService;

    @PostMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    //Flux is a reactive stream : Returns many small AI response chunks one-by-one (streaming)like, e.g. "Hel" -> "lo" -> " world"./ Flux: means stream of many values, not one final value
    // SSE(ServerSent Events) keeps the HTTP connection open and continuously pushes these chunks to the client (typing/live-streaming effect).
    public Flux<ServerSentEvent<StreamResponse>> streamChat(
          @RequestBody ChatRequest request  ) {

        return aiGenerationService.streamResponse(request.message(), request.projectId())
                .map(data -> ServerSentEvent.<StreamResponse>builder()
                        .data(data)
                        .build());
    }

    //Fetch the Project Chat History
    @GetMapping("/projects/{projectId}")
    public ResponseEntity<List<ChatResponse>> getChatHistory(
            @PathVariable Long projectId) {

        return ResponseEntity.ok(chatService.getProjectChatHistory(projectId));
    }
}
