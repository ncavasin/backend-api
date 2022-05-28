package com.sip.api.utils.mock;

import com.sip.api.domains.enums.UserStatus;
import com.sip.api.domains.role.Role;
import com.sip.api.domains.user.User;

import java.time.LocalDate;
import java.util.Set;


public class UserMocking {
    public static User getRawUserWithStatusActiveByParams(Set<Role> roles) {
        return User.builder()
                .firstName("John")
                .lastName("Doe")
                .dni(55779900)
                .email("johndoe@gmail.com")
                .password("secret_password")
                .birthDate(LocalDate.of(1990, 6, 5))
                .phone(2323428756L)
                .roles(roles)
                .status(UserStatus.ACTIVE)
                .build();
    }
}
