package com.sip.api.services.impl;


import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.PreferenceShipmentsRequest;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.PreferencePayer;
import com.sip.api.dtos.mercadopago.PaymentDto;
import com.sip.api.dtos.mercadopago.PaymentRequestDto;
import com.sip.api.services.MercadoPagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MercadoPagoServiceImpl implements MercadoPagoService {
    private final PaymentClient paymentClient;

    @Override
    public PaymentDto pay(PaymentRequestDto paymentRequestDto) {
        Payment payment = paymentClient.create(null);
        PreferencePayer
        PreferenceShipmentsRequest preferenceShipmentsRequest = new PreferenceShipmentsRequest();
        return null;
    }
}
