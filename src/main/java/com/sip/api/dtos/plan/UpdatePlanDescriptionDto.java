package com.sip.api.dtos.plan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePlanDescriptionDto {
    @NotNull
    private String planId;
    @NotNull
    private String description;
}
