package com.sip.api.services;

import com.sip.api.dtos.UserCredentialsDto;
import com.sip.api.dtos.user.auth.AuthenticationDto;

public interface AuthService {

    AuthenticationDto authenticate(UserCredentialsDto userCredentialsDto);

}
