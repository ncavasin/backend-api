package com.sip.api.controllers;

import com.sip.api.domains.payment.PaymentConverter;
import com.sip.api.dtos.payment.PaymentDto;
import com.sip.api.services.PaymentService;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/create-preference/{subscriptionId}")
    public void createPaymentPreference(@PathVariable("subscriptionId") String subscriptionId) {
        paymentService.createPaymentReference(subscriptionId);
    }

    @GetMapping("/feedback/{paymentId}")
    public void getPaymentFeedback(@PathVariable("paymentId") String paymentId) {
        paymentService.paymentFeedback(paymentId);
    }
}
