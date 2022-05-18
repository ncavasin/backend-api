package com.sip.api.dtos.activity;

import com.sip.api.dtos.user.UserSlimDto;
import lombok.*;
import org.checkerframework.checker.index.qual.NonNegative;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivityCreationDto {
    private String name;
    @NonNegative
    private Double basePrice;
    @NonNull
    private UserSlimDto professor;
    @NonNegative
    private int attendeesLimit;
}
