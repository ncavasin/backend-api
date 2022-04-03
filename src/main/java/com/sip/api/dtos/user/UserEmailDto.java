package com.sip.api.dtos.user;

import lombok.*;

import javax.validation.constraints.Email;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEmailDto {

    @NonNull
    @Email
    private String email;
}
