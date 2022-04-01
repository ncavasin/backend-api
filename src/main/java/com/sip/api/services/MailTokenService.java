package com.sip.api.services;

import com.sip.api.domains.registration.MailToken;
import com.sip.api.domains.user.User;

public interface MailTokenService {
    String createTokenForUser(User user);

    MailToken findByToken(String token);

    void confirmToken(String token);
}
