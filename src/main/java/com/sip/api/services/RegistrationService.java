package com.sip.api.services;

import com.sip.api.domains.user.User;
import com.sip.api.dtos.user.UserCreationDto;

public interface RegistrationService {
    User register(UserCreationDto userCreationDto);
}
