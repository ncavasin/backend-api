package com.sip.api.controllers;

import com.sip.api.domains.user.UserConverter;
import com.sip.api.dtos.TokenDto;
import com.sip.api.dtos.user.UserCreationDto;
import com.sip.api.dtos.user.UserDto;
import com.sip.api.services.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public UserDto register(@RequestBody @Validated UserCreationDto userCreationDto) {
        return UserConverter.entityToDto(registrationService.register(userCreationDto));
    }

    @PostMapping("/confirm")
    public UserDto confirmUser(@RequestBody @Validated TokenDto tokenDto){
        return UserConverter.entityToDto(registrationService.confirm(tokenDto));
    }
}
