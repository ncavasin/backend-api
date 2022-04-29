package com.sip.api.dtos;

import lombok.Builder;

@Builder
public record UserCredentialsDto(String email, String password) {
}
