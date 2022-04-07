package com.sip.api.controllers;

import com.sip.api.dtos.UserCredentialsDto;
import com.sip.api.dtos.user.auth.AuthenticationDto;
import com.sip.api.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public AuthenticationDto login(@RequestBody @Valid UserCredentialsDto userCredentialsDto) {
        return authService.authenticate(userCredentialsDto);
    }
}
