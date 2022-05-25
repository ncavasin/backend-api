package com.sip.api.services.impl;


import com.mercadopago.client.payment.PaymentClient;
import com.sip.api.dtos.mercadopago.PaymentDto;
import com.sip.api.dtos.mercadopago.PaymentRequestDto;
import com.sip.api.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentClient paymentClient;

    @Override
    public void processPayment() {

    }

    @Override
    public PaymentDto pay(PaymentRequestDto paymentRequestDto) {
//        Payment payment = paymentClient.create(null);
//        PreferenceShipmentsRequest preferenceShipmentsRequest = new PreferenceShipmentsRequest();
        return null;
    }

    @Override
    public void paymentFeedback(String paymentId) {
        
    }
}
