package com.priyansu.project.lovable_clone.service.impl;

import com.priyansu.project.lovable_clone.dto.auth.AuthResponse;
import com.priyansu.project.lovable_clone.dto.subscription.PlanLimitsResponse;
import com.priyansu.project.lovable_clone.dto.subscription.UsageTodayResponse;
import com.priyansu.project.lovable_clone.service.UsageService;
import com.priyansu.project.lovable_clone.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public AuthResponse getProfile(Long userId) {
        return null;
    }
}
