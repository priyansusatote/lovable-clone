package com.priyansu.project.lovable_clone.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
@ToString
public class ChatSessionId implements Serializable {
    Long projectId;
    Long userId;
}
