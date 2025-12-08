package com.priyansu.project.lovable_clone.service.impl;

import com.priyansu.project.lovable_clone.dto.PlanResponse;
import com.priyansu.project.lovable_clone.service.PlanService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanServiceImpl implements PlanService {
    @Override
    public List<PlanResponse> getAllActivePlans() {
        return List.of();
    }
}
