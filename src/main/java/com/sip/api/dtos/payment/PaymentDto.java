package com.sip.api.dtos.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
    private String id;
    private String transactionId;
    private Double amountPaid;
    private String subscriptionId;
    private LocalDate paymentDate;
    private List<PaymentStatusDto> paymentStatuses;
}
