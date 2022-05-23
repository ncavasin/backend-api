package com.sip.api.services;

import com.sip.api.dtos.mercadopago.PaymentDto;
import com.sip.api.dtos.mercadopago.PaymentRequestDto;

public interface MercadoPagoService {

    PaymentDto pay(PaymentRequestDto paymentRequestDto);

}
