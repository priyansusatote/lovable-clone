package com.priyansu.project.lovable_clone.dto;

import com.priyansu.project.lovable_clone.entity.Plan;
import com.priyansu.project.lovable_clone.entity.User;
import com.priyansu.project.lovable_clone.enums.SubscriptionStatus;

import java.time.Instant;

public record SubscriptionResponse(

        PlanResponse plan,

        String status,

        Instant periodEnd,

        Long tokenUsedThisCycle


) {
}
