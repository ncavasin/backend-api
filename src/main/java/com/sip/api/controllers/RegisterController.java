package com.sip.api.controllers;

import com.sip.api.domains.user.UserConverter;
import com.sip.api.dtos.user.UserCreationDto;
import com.sip.api.dtos.user.UserDto;
import com.sip.api.services.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    @PostMapping
    public UserDto register(@RequestBody @Valid UserCreationDto userCreationDto) {
        return UserConverter.entityToDto(registerService.register(userCreationDto));
    }

    @GetMapping("/confirm")
    public void confirm(@RequestParam("token") String token) {
        registerService.sendActivationEmail(token);
    }

    @PostMapping("/resend/{userId}")
    public void resend(@PathVariable("userId") String userId) {
        registerService.resendActivationEmail(userId);
    }
}
