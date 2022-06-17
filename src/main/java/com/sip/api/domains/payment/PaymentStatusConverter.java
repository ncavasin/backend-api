package com.sip.api.domains.payment;

import com.sip.api.dtos.payment.PaymentStatusDto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PaymentStatusConverter {
    public static List<PaymentStatusDto> fromEntityToDto(Set<PaymentStatus> paymentStatuses) {
        return paymentStatuses.stream().map(PaymentStatusConverter::fromEntityToDto).collect(Collectors.toList());
    }

    public static PaymentStatusDto fromEntityToDto(PaymentStatus paymentStatus) {
        return PaymentStatusDto.builder()
                .paymentId(paymentStatus.getPayment().getId())
                .initTimestamp(paymentStatus.getInitTimestamp())
                .paymentStatus(paymentStatus.getPaymentStatus())
                .endTimestamp(paymentStatus.getEndTimestamp())
                .isCurrent(paymentStatus.isCurrent())
                .build();
    }
}
