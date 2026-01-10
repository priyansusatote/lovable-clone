package com.priyansu.project.lovable_clone.service.impl;

import com.priyansu.project.lovable_clone.dto.subscription.CheckoutRequest;
import com.priyansu.project.lovable_clone.dto.subscription.CheckoutResponse;
import com.priyansu.project.lovable_clone.dto.subscription.PortalResponse;
import com.priyansu.project.lovable_clone.entity.Plan;
import com.priyansu.project.lovable_clone.entity.User;
import com.priyansu.project.lovable_clone.exception.ResourceNotFoundException;
import com.priyansu.project.lovable_clone.repository.PlanRepository;
import com.priyansu.project.lovable_clone.repository.UserRepository;
import com.priyansu.project.lovable_clone.security.AuthUtil;
import com.priyansu.project.lovable_clone.service.PaymentProcessor;
import com.stripe.exception.StripeException;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class StripePaymentProcessorImpl implements PaymentProcessor {

    private final AuthUtil authUtil;
    private final PlanRepository planRepository;
    private final UserRepository userRepository;

    @Value("${client.url}")
    private String frontendUrl;

    //goal of function: as user click on button : user Redirected to another page(stripe payment page)
    @Override
    public CheckoutResponse createCheckoutSessionUrl(CheckoutRequest request) {
        //check plan id valid or not
        Plan plan = planRepository.findById(request.planId())
                .orElseThrow(() -> new ResourceNotFoundException("Plan", request.planId().toString()));


        Long userId = authUtil.getCurrentUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", userId.toString()));

        //copied from stripe: Create a Checkout Session (goal is to crate checkout session url)
        var params = SessionCreateParams.builder()
                .addLineItem(
                        SessionCreateParams.LineItem.builder().setPrice(plan.getStripePriceId()).setQuantity(1L).build())
                .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                .setSubscriptionData(
                        new SessionCreateParams.SubscriptionData.Builder()
                                .setBillingMode(SessionCreateParams.SubscriptionData.BillingMode.builder()
                                        .setType(SessionCreateParams.SubscriptionData.BillingMode.Type.FLEXIBLE)
                                        .build())
                                .build()
                )
                .setSuccessUrl(frontendUrl + "/success.html?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl(frontendUrl + "/cancel.html")
                .putMetadata("user_id", userId.toString())
                .putMetadata("plan_id", plan.getId().toString());

        try {
            String stripeCustomerId = user.getStripeCustomerId();

            if(stripeCustomerId == null || stripeCustomerId.isEmpty()){
                params.setCustomerEmail(user.getUsername()); //new user -> stripe creates new Customer
            }else{
                params.setCustomerEmail(stripeCustomerId); //we provide StripeCustomerId (if u provide stripe Customer id for same user again&again stripe will not create duplicate customer Consider as a one
            }

            Session session = Session.create(params.build()); //SDK making API call to Stripe server

            return new CheckoutResponse(session.getUrl());

        } catch (StripeException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public PortalResponse openCustomerPortal(Long userId) {
        return null;
    }

    @Override
    public void handleWebhookEvent(String type, StripeObject stripeObject, Map<String, String> metadata) {

    }
}
