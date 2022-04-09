package com.sip.api.services.impl;

import com.sip.api.domains.registration.MailToken;
import com.sip.api.domains.role.Role;
import com.sip.api.domains.user.User;
import com.sip.api.domains.user.UserConverter;
import com.sip.api.dtos.user.UserCreationDto;
import com.sip.api.dtos.user.UserDto;
import com.sip.api.services.MailSender;
import com.sip.api.services.MailTokenService;
import com.sip.api.services.RegisterService;
import com.sip.api.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {
    private final UserService userService;
    private final MailTokenService mailTokenService;
    private final MailSender mailSender;

    @Override
    @Transactional
    public User register(UserCreationDto userCreationDto) {
        userCreationDto.setRolesNames(Collections.singletonList(
                Role.builder()
                        .name("USER")
                        .build()
                        .getName()));
        User savedUser = userService.createUser(userCreationDto);
        sendActivationMail(savedUser);
        return savedUser;
    }

    @Override
    @Transactional
    public void sendActivationEmail(String token) {
        MailToken mailToken = mailTokenService.findByToken(token);
        mailTokenService.confirmToken(token);
        userService.activateUser(mailToken.getUser().getId());
    }

    @Override
    public void resendActivationEmail(String userId) {
        deleteAssociatedTokens(userId);
        sendActivationMail(userService.findById(userId));
    }

    private void sendActivationMail(User user) {
        mailSender.sendConfirmationMail(user.getEmail(), user.getFirstName(), mailTokenService.createTokenForUser(user));
    }

    private void deleteAssociatedTokens(String userId) {
        List<MailToken> mailTokens = mailTokenService.findAllByUserId(userId);
        mailTokens.forEach(mailToken -> mailTokenService.deleteToken(mailToken.getId()));
    }
}
