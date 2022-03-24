package com.sip.api.dtos.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class UserPasswordDto {
    @NonNull
    private String password;
}