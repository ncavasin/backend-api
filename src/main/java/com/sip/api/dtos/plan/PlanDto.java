package com.sip.api.dtos.plan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanDto {
    private String id;
    private String name;
    private String description;
    private Double price;
    private Integer activitiesLimit;
}
