package com.sip.api.controllers;

import com.sip.api.domains.subscription.SubscriptionConverter;
import com.sip.api.dtos.subscription.SubscriptionCreationDto;
import com.sip.api.dtos.subscription.SubscriptionDto;
import com.sip.api.services.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subscription")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @GetMapping("/all")
    public List<SubscriptionDto> getAllSubscriptions() {
        return SubscriptionConverter.fromEntityToDto(subscriptionService.getAllSubscriptions());
    }

    @GetMapping("/{subscriptionId}")
    public SubscriptionDto findById(@PathVariable("subscriptionId") String subscriptionId) {
        return SubscriptionConverter.fromEntityToDto(subscriptionService.findSubscriptionById(subscriptionId));
    }

    @GetMapping("/from-user/{userId}")
    public List<SubscriptionDto> findByUserId(@PathVariable("userId") String userId) {
        return SubscriptionConverter.fromEntityToDto(subscriptionService.findSubscriptionsByUserId(userId));
    }

    @PostMapping
    public SubscriptionDto createSubscription(@RequestBody @Validated SubscriptionCreationDto subscriptionCreationDto) {
        return SubscriptionConverter.fromEntityToDto(subscriptionService.createSubscription(subscriptionCreationDto));
    }

    @DeleteMapping("/{subscriptionId}")
    public void deleteSubscription(@PathVariable("subscriptionId") String subscriptionId) {
        subscriptionService.deleteSubscription(subscriptionId);
    }
}
