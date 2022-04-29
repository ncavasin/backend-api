package com.sip.api.dtos.user;

import lombok.Builder;
import lombok.NonNull;

import javax.validation.constraints.Email;

@Builder
public record UserEmailDto(@NonNull @Email String email) {
}
