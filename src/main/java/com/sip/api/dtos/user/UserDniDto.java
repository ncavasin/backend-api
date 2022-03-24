package com.sip.api.dtos.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
public class UserDniDto {
    @Min(1)
    @Max(value = 99999999)
    private int dni;
}
