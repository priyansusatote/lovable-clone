package com.priyansu.project.lovable_clone.dto;

//all details that have in plan(Entity)
public record PlanResponse(
        Long id,
        String name,


        Integer maxProjects,
        Integer maxTokensPerDay,

        Boolean unlimitedAi,

        String price
) {
}
