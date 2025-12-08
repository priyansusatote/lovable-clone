package com.priyansu.project.lovable_clone.service.impl;

import com.priyansu.project.lovable_clone.dto.SubscriptionResponse;
import com.priyansu.project.lovable_clone.dto.subscription.CheckoutRequest;
import com.priyansu.project.lovable_clone.dto.subscription.CheckoutResponse;
import com.priyansu.project.lovable_clone.dto.subscription.PortalResponse;
import com.priyansu.project.lovable_clone.service.SubscriptionService;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    @Override
    public CheckoutResponse createCheckoutSessionUrl(CheckoutRequest request, Long userId) {
        return null;
    }

    @Override
    public SubscriptionResponse getCurrentSubscription(Long userId) {
        return null;
    }

    @Override
    public PortalResponse openCustomerPortal(Long userId) {
        return null;
    }
}
