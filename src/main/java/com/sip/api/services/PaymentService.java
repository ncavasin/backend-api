package com.sip.api.services;

import com.sip.api.domains.payment.Payment;
import com.sip.api.dtos.mercadopago.PaymentDto;
import com.sip.api.dtos.mercadopago.PaymentRequestDto;

import java.util.List;

public interface PaymentService {

    List<Payment> getAllPaymentsOfUser(String userId);

    void createPaymentReference(String subscriptionId);

    PaymentDto pay(PaymentRequestDto paymentRequestDto);

    void paymentFeedback(String paymentId);
}
