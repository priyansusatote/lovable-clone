package com.priyansu.project.lovable_clone.service.impl;

import com.priyansu.project.lovable_clone.dto.auth.AuthResponse;
import com.priyansu.project.lovable_clone.dto.subscription.PlanLimitsResponse;
import com.priyansu.project.lovable_clone.dto.subscription.UsageTodayResponse;
import com.priyansu.project.lovable_clone.repository.UserRepository;
import com.priyansu.project.lovable_clone.service.UsageService;
import com.priyansu.project.lovable_clone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public AuthResponse getProfile(Long userId) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
