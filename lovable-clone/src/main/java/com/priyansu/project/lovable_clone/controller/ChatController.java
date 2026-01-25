package com.priyansu.project.lovable_clone.controller;

import com.priyansu.project.lovable_clone.dto.chat.ChatRequest;
import com.priyansu.project.lovable_clone.service.AiGenerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final AiGenerationService aiGenerationService;

    @PostMapping(value = "/api/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    //Flux is a reactive stream : Returns many small AI response chunks one-by-one (streaming)like, e.g. "Hel" -> "lo" -> " world"./ Flux: means stream of many values, not one final value
    // SSE(ServerSent Events) keeps the HTTP connection open and continuously pushes these chunks to the client (typing/live-streaming effect).
    public Flux<ServerSentEvent<String>> streamChat(
          @RequestBody ChatRequest request  ) {

        return aiGenerationService.streamResponse(request.message(), request.projectId())
                .map(data -> ServerSentEvent.<String>builder()
                        .data(data)
                        .build());
    }
}
