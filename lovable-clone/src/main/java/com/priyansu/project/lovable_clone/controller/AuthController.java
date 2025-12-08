package com.priyansu.project.lovable_clone.controller;

import com.priyansu.project.lovable_clone.dto.auth.AuthResponse;
import com.priyansu.project.lovable_clone.dto.auth.LoginRequest;
import com.priyansu.project.lovable_clone.dto.auth.SignupRequest;
import com.priyansu.project.lovable_clone.dto.auth.UserProfileResponse;
import com.priyansu.project.lovable_clone.service.AuthService;
import com.priyansu.project.lovable_clone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")

public class AuthController {
    //DI
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/signup")
   public ResponseEntity<AuthResponse> signup(@RequestBody SignupRequest request){ // SignupRequest = client input (includes password)  AuthResponse = server output (no password) Two DTOs because request and response fields are different for security.
       return ResponseEntity.ok(authService.singup(request));
   }

   @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
   }

   @GetMapping("/me")
    public ResponseEntity<AuthResponse> getProfile(){
        Long userId= 1L;  //temporary taken this
        return ResponseEntity.ok(userService.getProfile(userId));
   }
}
