package com.sip.api.services.impl;

import com.sip.api.domains.plan.Plan;
import com.sip.api.domains.subscription.Subscription;
import com.sip.api.domains.user.User;
import com.sip.api.dtos.subscription.SubscriptionCreationDto;
import com.sip.api.exceptions.BadRequestException;
import com.sip.api.exceptions.NotFoundException;
import com.sip.api.repositories.SubscriptionRepository;
import com.sip.api.services.PlanService;
import com.sip.api.services.SubscriptionService;
import com.sip.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public Subscription findSubscriptionById(String subscriptionId) {
        return subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new NotFoundException("Subscription not found"));
    }

    @Override
    public List<Subscription> findSubscriptionsByUserId(String userId) {
        return subscriptionRepository.findAllByUserId(userId);
    }

    @Override
    public List<Subscription> findSubscriptionsByPlanId(String planId) {
        return subscriptionRepository.findAllByPlanId(planId);
    }

    @Override
    public Subscription createSubscription(SubscriptionCreationDto subscriptionCreationDto) {
        Plan plan = planService.findById(subscriptionCreationDto.getPlanId());
        User user = userService.findById(subscriptionCreationDto.getUserId());
        if (subscriptionCreationDto.getMonthsToAdd() < 1)
            throw new BadRequestException("Months to add must be greater than 0");

        final LocalDate endDate = subscriptionCreationDto.getStartDate().plusMonths(subscriptionCreationDto.getMonthsToAdd());

        if (isOverlappingWithUserIdAndPlanIdWithinDates(user.getId(), plan.getId(), subscriptionCreationDto.getStartDate(), endDate))
            throw new BadRequestException("User new Subscription overlaps with an existing subscription for the same plan within the same period");

        return subscriptionRepository.save(Subscription.builder()
                .description(subscriptionCreationDto.getDescription())
                .startDate(subscriptionCreationDto.getStartDate())
                .endDate(subscriptionCreationDto.getStartDate().plusMonths(subscriptionCreationDto.getMonthsToAdd()))
                .plan(plan)
                .user(user)
                FIX THIS TO BE CREATED AS UNPAID
                .payment(null)
                .build());
    }

    private boolean isOverlappingWithUserIdAndPlanIdWithinDates(String userId, String planId, LocalDate startDate, LocalDate endDate) {
        List<Subscription> subscriptions = subscriptionRepository.findAllByUserIdAndPlanId(userId, planId);

        if (subscriptions.isEmpty())
            return false;

        // Verify that dates do not overlap
        return subscriptions.stream()
                .anyMatch(subscription ->
                        ((startDate.isBefore(subscription.getStartDate()) || startDate.isEqual(subscription.getStartDate()))
                                && (endDate.isAfter(subscription.getStartDate()) || endDate.isEqual(subscription.getStartDate())))
                                && (endDate.isBefore(subscription.getEndDate()) || endDate.isEqual(subscription.getEndDate()))
                                ||
                                ((startDate.isAfter(subscription.getStartDate()) || startDate.isEqual(subscription.getStartDate()))
                                        && (startDate.isBefore(subscription.getEndDate()) || startDate.isEqual(subscription.getEndDate()))
                                        && (endDate.isAfter(subscription.getEndDate()) || endDate.isEqual(subscription.getEndDate()))));
    }

    @Override
    public void deleteSubscription(String subscriptionId) {
        if (!subscriptionRepository.existsById(subscriptionId))
            throw new NotFoundException("Subscription not found");
        subscriptionRepository.deleteById(subscriptionId);
    }

    @Override
    public void deleteAllSubscriptionsFromUser(String userId) {
        subscriptionRepository.deleteAllByUserId(userId);
    }
}
