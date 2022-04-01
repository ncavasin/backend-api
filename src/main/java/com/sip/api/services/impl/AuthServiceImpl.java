package com.sip.api.services.impl;

import com.sip.api.domains.user.User;
import com.sip.api.dtos.UserCredentialsDto;
import com.sip.api.dtos.user.UserDniDto;
import com.sip.api.dtos.user.auth.AuthenticationDto;
import com.sip.api.exceptions.NotFoundException;
import com.sip.api.exceptions.UnauthorizedException;
import com.sip.api.services.AuthService;
import com.sip.api.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;

    @Override
    public AuthenticationDto authenticate(UserCredentialsDto userCredentialsDto) {
        // Find user by dni
        User user;
        try {
            user = userService.findByDni(UserDniDto.builder()
                    .dni(userCredentialsDto.getDni())
                    .build());
        } catch (NotFoundException e) {
            throw new UnauthorizedException("User not found");
        }

        // Validate password


        // Return authentication

        return null;
    }
}
