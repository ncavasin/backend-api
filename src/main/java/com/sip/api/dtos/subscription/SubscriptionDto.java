package com.sip.api.dtos.subscription;

import com.sip.api.dtos.payment.PaymentDto;
import com.sip.api.dtos.plan.PlanDto;
import com.sip.api.dtos.user.UserSlimDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDto {
    private String id;
    private LocalDate startDate;
    private LocalDate endDate;
    private PlanDto planDto;
    private PaymentDto paymentDto;
    private UserSlimDto userSlimDto;
}

