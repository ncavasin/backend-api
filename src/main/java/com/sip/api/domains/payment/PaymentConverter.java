package com.sip.api.domains.payment;

import com.sip.api.domains.subscription.SubscriptionConverter;
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
        if (payment == null) return null;
        return PaymentDto.builder()
                .id(payment.getId())
                .transactionId(payment.getTransactionId())
                .subscriptionDto(SubscriptionConverter.fromEntityToDto(payment.getSubscription()))
                .paymentDate(payment.getPaymentDate())
                .amountPaid(payment.getAmountPaid())
                .paymentStatus(payment.getPaymentStatus())
                .build();
    }
}
