package com.sip.api.dtos.user;

import com.sip.api.domains.enums.UserStatus;
import lombok.Builder;

import java.util.Set;

@Builder
public record UserDto(String id, int dni, String email, String firstName, String lastName, int age, int phone,
                      UserStatus status, Set<String> roles) {
}
