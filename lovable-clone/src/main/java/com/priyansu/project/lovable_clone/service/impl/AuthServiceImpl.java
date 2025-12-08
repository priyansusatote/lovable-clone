package com.priyansu.project.lovable_clone.service.impl;

import com.priyansu.project.lovable_clone.dto.auth.AuthResponse;
import com.priyansu.project.lovable_clone.dto.auth.LoginRequest;
import com.priyansu.project.lovable_clone.dto.auth.SignupRequest;
import com.priyansu.project.lovable_clone.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public AuthResponse singup(SignupRequest request) {
        return null;
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        return null;
    }
}
