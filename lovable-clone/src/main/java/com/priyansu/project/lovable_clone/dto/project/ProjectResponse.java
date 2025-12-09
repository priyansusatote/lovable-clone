package com.priyansu.project.lovable_clone.dto.project;

import com.priyansu.project.lovable_clone.dto.auth.UserProfileResponse;

import java.time.Instant;
import java.time.LocalDateTime;

public record ProjectResponse(
        Long id,
        String name,
        Boolean isPublic,
        Long ownerId,
        UserProfileResponse owner,
        Instant createdAt,
        Instant updatedAt

) {
}
