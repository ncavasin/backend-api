package com.sip.api.services.impl;

import com.sip.api.dtos.user.UserCreationDto;
import com.sip.api.services.RegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    @Override
    public String register(UserCreationDto userCreationDto) {
        return "WORKING";
    }
}
