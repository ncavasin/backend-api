package com.sip.api.services;

import com.sip.api.dtos.user.UserCreationDto;

public interface RegistrationService {
    String register(UserCreationDto userCreationDto);
}
