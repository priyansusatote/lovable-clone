package com.priyansu.project.lovable_clone.service;

import com.priyansu.project.lovable_clone.dto.PlanResponse;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface PlanService {
    List<PlanResponse> getAllActivePlans();
}
