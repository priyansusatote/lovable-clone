package com.priyansu.project.lovable_clone.dto;

//all details that have in plan(Entity)
public record PlanResponse(
        Long id,
        String name,

        String stripePriceId,
        Integer maxProjects,
        Integer maxTokensPerDay,
        Integer maxPreviews,
        Boolean unlimitedAi,

        Boolean active
) {
}
