package com.priyansu.project.lovable_clone.entity;


import com.priyansu.project.lovable_clone.enums.MessageRole;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@FieldDefaults(level =  AccessLevel.PRIVATE)

public class ChatMessage {

    Long id;
    ChatSession charSession;
    String content;

    MessageRole role;

    String  toolCalls;     //JSON Array of Tools Called

    Integer tokenUsed;
    Instant createdAt;

}
