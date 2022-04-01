package com.sip.api.services.impl;

import com.sip.api.domains.registration.MailToken;
import com.sip.api.domains.user.User;
import com.sip.api.dtos.TokenDto;
import com.sip.api.dtos.user.UserCreationDto;
import com.sip.api.services.MailTokenService;
import com.sip.api.services.RegisterService;
import com.sip.api.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {
    private final UserService userService;
    private final MailTokenService mailTokenService;

    @Override
    @Transactional
    public User register(UserCreationDto userCreationDto) {
        User savedUser = userService.createUser(userCreationDto);
        TokenDto tokenDto = mailTokenService.createTokenForUser(savedUser);
        // TODO: send mail
        return savedUser;
    }

    @Override
    @Transactional
    public User confirm(TokenDto tokenDto) {
        MailToken mailToken = mailTokenService.findByToken(tokenDto.getToken());
        return userService.activateUser(mailToken.getUser().getId());
    }


}
