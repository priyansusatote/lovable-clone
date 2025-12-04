package com.priyansu.project.lovable_clone.dto.subscription;

public record PlanLimitsResponse(
        String planName,
        int maxTokenPerDay,
        int maxProjects,
        boolean unlimitedAi
) {
}
