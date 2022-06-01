package com.sip.api.dtos.plan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanCreationDto {
    @NotNull
    private String name;
    private String description;
    @Positive
    private Double price;
    @Positive
    private Integer activitiesLimit;
}
