package com.sip.api.dtos.mercadopago;

import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {

    private final PaymentClient paymentClient;

    PaymentCreateRequest paymentCreateRequest = PaymentCreateRequest.builder()
            .description("Pago de prueba")
            .externalReference()
            .installments()
            .order()
            .payer()
            .paymentMethodId()
            .transactionAmount()
            .build();
}
