package com.sip.api.dtos.activity;

import com.sip.api.domains.availableClass.AvailableClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.index.qual.NonNegative;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivityCreationDto {
    private String name;
    @NonNegative
    private Double basePrice;
    private AvailableClass availableClass;
    @NonNegative
    private int atendeesLimit;
}
