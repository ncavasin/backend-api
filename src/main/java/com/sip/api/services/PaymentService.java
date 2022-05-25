package com.sip.api.services;

import com.sip.api.dtos.mercadopago.PaymentDto;
import com.sip.api.dtos.mercadopago.PaymentRequestDto;

public interface PaymentService {

    void processPayment();

    PaymentDto pay(PaymentRequestDto paymentRequestDto);


    void paymentFeedback(String paymentId);
}
