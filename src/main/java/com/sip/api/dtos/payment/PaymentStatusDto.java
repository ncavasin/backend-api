package com.sip.api.dtos.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentStatusDto {
    private String paymentId;
    private LocalDateTime initTimestamp;
    private String paymentStatus;
    private LocalDateTime endTimestamp;
    private boolean isCurrent;
}
