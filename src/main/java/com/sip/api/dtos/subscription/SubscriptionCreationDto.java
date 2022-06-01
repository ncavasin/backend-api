package com.sip.api.dtos.subscription;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionCreationDto {
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String planId;
    private String userId;
}
