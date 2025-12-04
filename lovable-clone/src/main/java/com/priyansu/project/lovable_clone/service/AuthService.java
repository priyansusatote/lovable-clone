package com.priyansu.project.lovable_clone.service;

import com.priyansu.project.lovable_clone.dto.auth.AuthResponse;
import com.priyansu.project.lovable_clone.dto.auth.LoginRequest;
import com.priyansu.project.lovable_clone.dto.auth.SignupRequest;

public interface AuthService {

     AuthResponse singup(SignupRequest request);

     AuthResponse login(LoginRequest request);
}
