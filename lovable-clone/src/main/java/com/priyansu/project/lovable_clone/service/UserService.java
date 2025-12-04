package com.priyansu.project.lovable_clone.service;

import com.priyansu.project.lovable_clone.dto.auth.AuthResponse;
import com.priyansu.project.lovable_clone.dto.auth.UserProfileResponse;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;


public interface UserService {
     AuthResponse getProfile(Long userId);
}
