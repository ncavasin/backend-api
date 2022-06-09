package com.sip.api.domains.payment;

import com.sip.api.dtos.payment.PaymentDto;

import java.util.List;
import java.util.stream.Collectors;

public class PaymentConverter {

    public static List<PaymentDto> fromEntityToDto(List<Payment> payments) {
        return payments.stream()
                .map(PaymentConverter::fromEntityToDto)
                .collect(Collectors.toList());
    }

    public static PaymentDto fromEntityToDto(Payment payment) {
        return PaymentDto.builder()
                .id(payment.getId())
                .build();
    }
}
