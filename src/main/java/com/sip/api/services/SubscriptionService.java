package com.sip.api.services;

import com.sip.api.domains.subscription.Subscription;
import com.sip.api.dtos.subscription.SubscriptionCreationDto;

import java.util.List;

public interface SubscriptionService {
    List<Subscription> getAllSubscriptions();

    List<Subscription> findSubscriptionsByUserId(String userId);

    Subscription findSubscriptionById(String subscriptionId);

    Subscription createSubscription(SubscriptionCreationDto subscriptionCreationDto);

    void deleteSubscription(String subscriptionId);
}
