package com.sip.api.dtos.availableClass;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvailableClassAttendeeAmountDto {
    private Integer attendeeAmount;
}
