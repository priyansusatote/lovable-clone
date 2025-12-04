package com.priyansu.project.lovable_clone.dto.project;

import java.time.Instant;

public record ProjectSummeryResponse(
        Long id,
        Long name,
        Instant createdAt,
        Instant updatedAt
) {
}
