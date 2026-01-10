package com.priyansu.project.lovable_clone.entity;

import com.priyansu.project.lovable_clone.enums.SubscriptionStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)

@AllArgsConstructor
@NoArgsConstructor
public class Subscription {


    Long id;

    User user; //each subscription can have 1 user

    Plan plan;

    SubscriptionStatus status;


    String stripeSubscriptionId;

    Instant currentPeriodStart;
    Instant currentPeriodEnd;
    Boolean cancelAtPeriodEnd;

    Instant createdAt;
    Instant updatedAt;


}
