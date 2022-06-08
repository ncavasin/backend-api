package com.sip.api.services;

import com.sip.api.domains.subscription.Subscription;
import com.sip.api.dtos.subscription.SubscriptionCreationDto;

import java.util.List;

public interface SubscriptionService {
    List<Subscription> getAllSubscriptions();

    Subscription findSubscriptionById(String subscriptionId);

    List<Subscription> findSubscriptionsByPlanId(String planId);

    List<Subscription> findSubscriptionsByUserId(String userId);

    Subscription createSubscription(SubscriptionCreationDto subscriptionCreationDto);

    void deleteSubscription(String subscriptionId);

    void deleteAllSubscriptionsFromUser(String userId);
}
