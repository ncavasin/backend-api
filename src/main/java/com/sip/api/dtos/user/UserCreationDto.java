package com.sip.api.dtos.user;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationDto {
    @Min(1)
    @Max(value = 99999999)
    private int dni;

    @NonNull
    private String password;

    @Email
    @NonNull
    private String email;

    private String firstName;

    private String lastName;

    @Min(1)
    @Max(99)
    private int age;

    private int phone;

    private List<String> rolesNames;
}
