package com.sip.api.dtos.user;

import lombok.Builder;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Builder
public record UserDniDto(@Min(1) @Max(value = 99999999) int dni) {
}
