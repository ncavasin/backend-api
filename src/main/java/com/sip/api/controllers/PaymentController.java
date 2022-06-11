package com.sip.api.controllers;

import com.mercadopago.resources.preference.Preference;
import com.sip.api.domains.payment.PaymentConverter;
import com.sip.api.dtos.mercadopago.PaymentNotificationDto;
import com.sip.api.dtos.payment.PaymentDto;
import com.sip.api.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/from-user/{userId}")
    public List<PaymentDto> getAllPaymentsOfUser(@PathVariable("userId") String userId) {
        return PaymentConverter.fromEntityToDto(paymentService.getAllPaymentsOfUser(userId));
    }

    @GetMapping("/all")
    public List<PaymentDto> getAll() {
        return PaymentConverter.fromEntityToDto(paymentService.getAll());
    }

    @GetMapping("/{paymentId}")
    public PaymentDto getById(@PathVariable("paymentId") String paymentId) {
        return PaymentConverter.fromEntityToDto(paymentService.findById(paymentId));
    }

    @GetMapping("/from-plan/{planId}")
    public List<PaymentDto> getAllPaymentsOfPlan(@PathVariable("planId") String planId) {
        return PaymentConverter.fromEntityToDto(paymentService.getAllPaymentsOfPlan(planId));
    }

    @GetMapping("/from-subscription/{subscriptionId}")
    public PaymentDto getPaymentOfSubscription(@PathVariable("subscriptionId") String subscriptionId) {
        return PaymentConverter.fromEntityToDto(paymentService.getPaymentBySubscriptionId(subscriptionId));
    }

    @GetMapping("/create-preference/{subscriptionId}")
    public Preference createPaymentPreference(@PathVariable("subscriptionId") String subscriptionId) {
        return paymentService.createPaymentReference(subscriptionId);
    }

    @PostMapping("/notification")
    public void paymentFeedback(@RequestParam("subscriptionId") String subscriptionId, @RequestBody @Validated PaymentNotificationDto paymentNotificationDto) {
        paymentService.paymentNotification(subscriptionId, paymentNotificationDto);
    }
}
