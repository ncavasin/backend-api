package com.sip.api.dtos.payment;

import com.sip.api.domains.subscription.Subscription;
import com.sip.api.dtos.subscription.SubscriptionDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
    private String id;
    private String transactionId;
    private Double amountPaid;
    private SubscriptionDto subscriptionDto;
    private LocalDate paymentDate;
    private String paymentStatus;
}
