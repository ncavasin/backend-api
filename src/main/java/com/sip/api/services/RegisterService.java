package com.sip.api.services;

import com.sip.api.domains.user.User;
import com.sip.api.dtos.user.UserCreationDto;
import com.sip.api.dtos.user.UserDto;

public interface RegisterService {
    User register(UserCreationDto userCreationDto);

    void sendActivationEmail(String token);

    void resendActivationEmail(String userId);
}
