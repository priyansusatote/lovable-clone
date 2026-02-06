package com.priyansu.project.lovable_clone.dto.chat;

import com.priyansu.project.lovable_clone.entity.ChatEvent;
import com.priyansu.project.lovable_clone.entity.ChatSession;
import com.priyansu.project.lovable_clone.enums.MessageRole;


import java.time.Instant;
import java.util.List;

public record ChatResponse(

        Long id,
        String content,
        MessageRole role,
        List<ChatEventResponse> events,
        Integer tokenUsed,
        Instant createdAt
) {
}
