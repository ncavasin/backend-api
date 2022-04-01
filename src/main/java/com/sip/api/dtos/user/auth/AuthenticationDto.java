package com.sip.api.dtos.user.auth;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
public class AuthenticationDto {
    @NonNull
    private String token;
}
