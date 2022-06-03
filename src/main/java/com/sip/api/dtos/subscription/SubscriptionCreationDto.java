package com.sip.api.dtos.subscription;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionCreationDto {
    private String description;
    @NotNull
    private LocalDate startDate;
    @Min(value = 1)
    private Integer monthsToAdd;
    @NotNull
    private String planId;
    @NotNull
    private String userId;
}
