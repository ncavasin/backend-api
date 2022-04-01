package com.sip.api.controllers;

import com.sip.api.domains.user.UserConverter;
import com.sip.api.dtos.user.UserCreationDto;
import com.sip.api.dtos.user.UserDto;
import com.sip.api.services.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    @PostMapping
    public UserDto register(@RequestBody @Validated UserCreationDto userCreationDto) {
        return UserConverter.entityToDto(registerService.register(userCreationDto));
    }

    @GetMapping("/confirm")
    public UserDto confirm(@RequestParam("token") String token) {
        return UserConverter.entityToDto(registerService.confirm(token));
    }
}
