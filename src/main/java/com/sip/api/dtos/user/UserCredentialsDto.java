package com.sip.api.dtos.user;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCredentialsDto {
    private String email;
    private String password;
}
