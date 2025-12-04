package com.priyansu.project.lovable_clone.dto.project;

import com.priyansu.project.lovable_clone.dto.auth.UserProfileResponse;

import java.time.Instant;

public record ProjectResponse(
        Long id,
        Long name,
        Instant createdAt,
        Instant updatedAt,
        UserProfileResponse owner
) {
}
