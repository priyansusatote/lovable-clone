package com.priyansu.project.lovable_clone.controller;

import com.priyansu.project.lovable_clone.dto.PlanResponse;
import com.priyansu.project.lovable_clone.dto.SubscriptionResponse;
import com.priyansu.project.lovable_clone.dto.subscription.CheckoutRequest;
import com.priyansu.project.lovable_clone.dto.subscription.CheckoutResponse;
import com.priyansu.project.lovable_clone.dto.subscription.PortalResponse;
import com.priyansu.project.lovable_clone.service.PlanService;
import com.priyansu.project.lovable_clone.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor

public class SubscriptionController {
    //DI
    private final SubscriptionService subscriptionService;
    private final PlanService planService;

    @GetMapping("/api/plans")
    public ResponseEntity<List<PlanResponse>> getAllPlans(){
        return ResponseEntity.ok(planService.getAllActivePlans());
    }

    @GetMapping("/api/me/subscription")
    public ResponseEntity<SubscriptionResponse> getMySubscription(){
        Long userId = 1L;
        return ResponseEntity.ok(subscriptionService.getCurrentSubscription(userId));
    }

    @PostMapping("/api/stripe/checkout")
    public ResponseEntity<CheckoutResponse> createCheckoutResponse(@RequestBody CheckoutRequest request){
        Long userId = 1L;
        return ResponseEntity.ok(subscriptionService.createCheckoutSessionUrl(request, userId));
    }

    @PostMapping("/api/stripe/portal")
    public ResponseEntity<PortalResponse> openCustomerPortal(){
        Long userId = 1L;
        return ResponseEntity.ok(subscriptionService.openCustomerPortal( userId));
    }

}
