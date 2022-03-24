package com.sip.api.dtos.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.Email;

@Data
@NoArgsConstructor
public class UserEmailDto {

    @NonNull
    @Email
    private String email;
}
