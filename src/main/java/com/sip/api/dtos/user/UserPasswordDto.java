package com.sip.api.dtos.user;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record UserPasswordDto(@NonNull String password) {
}