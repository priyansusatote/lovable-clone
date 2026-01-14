package com.priyansu.project.lovable_clone.repository;

import com.priyansu.project.lovable_clone.entity.Subscription;
import com.priyansu.project.lovable_clone.enums.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;
import java.util.Set;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    //Get the current active Subscription
    Optional<Subscription> findByUserIdAndStatusIn(long userId, Set<SubscriptionStatus> statusSet);

    boolean existsByStripeSubscriptionId(String subscriptionId);

    Optional<Subscription> findByStripeSubscriptionId(String gatewaySubscriptionId);
}
