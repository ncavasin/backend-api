package com.sip.api.services.impl;

import com.sip.api.domains.user.User;
import com.sip.api.dtos.user.UserCreationDto;
import com.sip.api.services.RegistrationService;
import com.sip.api.services.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final UserService userService;

    @Override
    public User register(UserCreationDto userCreationDto) {
        return userService.createUser(userCreationDto);
    }
}
