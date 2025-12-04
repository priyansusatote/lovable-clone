package com.priyansu.project.lovable_clone.service;

import com.priyansu.project.lovable_clone.dto.SubscriptionResponse;
import com.priyansu.project.lovable_clone.dto.subscription.CheckoutRequest;
import com.priyansu.project.lovable_clone.dto.subscription.CheckoutResponse;
import com.priyansu.project.lovable_clone.dto.subscription.PortalResponse;
import org.jspecify.annotations.Nullable;

public interface SubscriptionService {


    CheckoutResponse createCheckoutSessionUrl(CheckoutRequest request, Long userId);


    SubscriptionResponse getCurrentSubscription(Long userId);

     PortalResponse openCustomerPortal(Long userId);
}
