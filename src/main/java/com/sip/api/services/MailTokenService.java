package com.sip.api.services;

import com.sip.api.domains.registration.MailToken;
import com.sip.api.domains.user.User;

import java.util.List;

public interface MailTokenService {
    String createTokenForUser(User user);

    MailToken findByToken(String token);

    List<MailToken> findAllByUserId(String userId);

    void confirmToken(String token);

    void deleteToken(String tokenId);
}
