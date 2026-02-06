package com.priyansu.project.lovable_clone.dto.project;

import com.priyansu.project.lovable_clone.enums.ProjectRole;

import java.time.Instant;

public record ProjectSummeryResponse(
        Long id,
        String name,
        Instant createdAt,
        Instant updatedAt,
        ProjectRole role
) {
}
