package com.sip.api.dtos.user;

import com.sip.api.domains.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String id;
    private int dni;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private BigInteger phone;
    private UserStatus status;
    private Set<String> roles;
}
