package com.sip.api.services;

import com.sip.api.domains.registration.MailToken;
import com.sip.api.domains.user.User;
import com.sip.api.dtos.TokenDto;

public interface MailTokenService {
    TokenDto createTokenForUser(User user);

    MailToken findByToken(String token);

    void confirmToken(String token);
}
