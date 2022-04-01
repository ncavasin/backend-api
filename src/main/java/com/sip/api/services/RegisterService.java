package com.sip.api.services;

import com.sip.api.domains.user.User;
import com.sip.api.dtos.TokenDto;
import com.sip.api.dtos.user.UserCreationDto;

public interface RegisterService {
    User register(UserCreationDto userCreationDto);

    User confirm(TokenDto tokenDto);
}
