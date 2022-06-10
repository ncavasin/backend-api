package com.sip.api.domains.subscription;

import com.sip.api.domains.payment.PaymentConverter;
import com.sip.api.domains.plan.PlanConverter;
import com.sip.api.domains.user.UserConverter;
import com.sip.api.dtos.subscription.SubscriptionDto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SubscriptionConverter {

    public static List<SubscriptionDto> fromEntityToDto(List<Subscription> subscriptions) {
        return subscriptions.stream()
                .map(SubscriptionConverter::fromEntityToDto)
                .collect(Collectors.toList());
    }

    public static SubscriptionDto fromEntityToDto(Subscription subscription) {
        if (subscription == null) return null;
        return SubscriptionDto.builder()
                .id(subscription.getId())
                .startDate(subscription.getStartDate())
                .endDate(subscription.getEndDate())
                .planDto(PlanConverter.fromEntityToDto(subscription.getPlan()))
                .paymentDto(PaymentConverter.fromEntityToDto(subscription.getPayment()))
                .userSlimDto(UserConverter.entityToDtoSlim(subscription.getUser()))
                .build();
    }

    public static List<String> fromEntitiesToListOfIds(Set<Subscription> subscriptions) {
        return subscriptions.stream()
                .map(Subscription::getId)
                .collect(Collectors.toList());
    }
}
