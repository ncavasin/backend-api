package com.sip.api.services.impl;

import com.sip.api.domains.plan.Plan;
import com.sip.api.domains.subscription.Subscription;
import com.sip.api.domains.user.User;
import com.sip.api.dtos.subscription.SubscriptionCreationDto;
import com.sip.api.exceptions.NotFoundException;
import com.sip.api.repositories.SubscriptionRepository;
import com.sip.api.services.PlanService;
import com.sip.api.services.SubscriptionService;
import com.sip.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final PlanService planService;
    private final UserService userService;

    @Override
    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    @Override
    public List<Subscription> findSubscriptionsByUserId(String userId) {
        return subscriptionRepository.findAllByUserId(userId);
    }

    @Override
    public Subscription findSubscriptionById(String subscriptionId) {
        return subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new NotFoundException("Subscription not found"));
    }

    @Override
    public Subscription createSubscription(SubscriptionCreationDto subscriptionCreationDto) {
        Plan plan = planService.findById(subscriptionCreationDto.getPlanId());
        User user = userService.findById(subscriptionCreationDto.getUserId());

        return subscriptionRepository.save(Subscription.builder()
                .description(subscriptionCreationDto.getDescription())
                .startDate(subscriptionCreationDto.getStartDate())
                .endDate(subscriptionCreationDto.getStartDate().plusMonths(subscriptionCreationDto.getMonthsToAdd()))
                .plan(plan)
                .user(user)
                .build());
    }

    @Override
    public void deleteSubscription(String subscriptionId) {
        if (!subscriptionRepository.existsById(subscriptionId))
            throw new NotFoundException("Subscription not found");
        subscriptionRepository.deleteById(subscriptionId);
    }
}
