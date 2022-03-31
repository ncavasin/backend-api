package com.sip.api.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserCredentialsDto {
    private int dni;
    private String password;
}
