package com.sip.api.dtos.user;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationDto {
    @Size(max = 8)
    private int dni;

    @NonNull
    private String password;

    @Email
    @NonNull
    private String email;

    private String firstName;

    private String lastName;

    private int age;

    private int phone;

    private String zipCode;
}
