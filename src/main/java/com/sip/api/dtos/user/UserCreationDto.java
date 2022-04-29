package com.sip.api.dtos.user;

import lombok.Builder;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Builder
public record UserCreationDto(@Min(1) @Max(value = 99999999) int dni, @NonNull String password,
                              @Email @NonNull String email,
                              String firstName, String lastName, @Min(1) @Max(99) int age, int phone,
                              List<String> rolesNames) {
}
