package com.sip.api.controllers;

import com.sip.api.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/all")
    public void getAllPayments() {

    }

    @PostMapping("/pay")
    public void createPaymentPreference() {
        paymentService.processPayment();
    }

    @GetMapping("/feedback/{paymentId}")
    public void getPaymentFeedback(@PathVariable("paymentId") String paymentId) {
        paymentService.paymentFeedback(paymentId);
    }
}
