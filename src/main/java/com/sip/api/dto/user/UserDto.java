package com.sip.api.dto.user;

import com.sip.api.domain.user.User;
import com.sip.api.domain.enums.UserStatus;
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

    public static UserDto entityToDto(User user) {
        return UserDto.builder()
                .dni(user.getDni())
                .password(user.getPassword())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .age(user.getAge())
                .phone(user.getPhone())
                .zipCode(user.getZipCode())
                .status(user.getStatus())
                .build();
    }

    public static List<UserDto> entityToDto(List<User> users) {
        return users.stream().map(UserDto::entityToDto).collect(Collectors.toList());
    }
}
