package com.sip.api.dtos.user;

import com.sip.api.domains.user.User;
import com.sip.api.domains.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private int dni;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private int age;
    private int phone;
    private String zipCode;
    UserStatus status;
}
