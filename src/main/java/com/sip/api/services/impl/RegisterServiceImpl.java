package com.sip.api.services.impl;

import com.sip.api.domains.enums.UserStatus;
import com.sip.api.domains.registration.MailToken;
import com.sip.api.domains.role.Role;
import com.sip.api.domains.user.User;
import com.sip.api.dtos.user.UserCreationDto;
import com.sip.api.services.MailSender;
import com.sip.api.services.MailTokenService;
import com.sip.api.services.RegisterService;
import com.sip.api.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
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
        userCreationDto = buildUserWithUserRole(userCreationDto);
        User savedUser = userService.createUser(userCreationDto);
        sendActivationMail(savedUser);
        return savedUser;
    }

    private UserCreationDto buildUserWithUserRole(UserCreationDto userCreationDto) {
        return UserCreationDto.builder()
                .dni(userCreationDto.dni())
                .age(userCreationDto.age())
                .firstName(userCreationDto.firstName())
                .lastName(userCreationDto.lastName())
                .email(userCreationDto.email())
                .password(userCreationDto.password())
                .phone(userCreationDto.phone())
                .rolesNames(Collections.singletonList(
                        Role.builder()
                                .name("ROLE_USER")
                                .build()
                                .getName()))
                .build();
    }

    @Override
    public void confirmEmail(String token) {
        MailToken mailToken = mailTokenService.findByToken(token);
        mailTokenService.confirmToken(token);
        userService.activateUser(mailToken.getUser().getId());
    }

    @Override
    public void resendActivationEmail(String userId) {
        deleteAssociatedTokens(userId);
        sendActivationMail(userService.findById(userId));
    }

    @Override
    @Async
    public void sendActivationMail(User user) {
        if (user.getStatus() == UserStatus.INACTIVE) {
            try {
                new Thread(() -> {
                    try {
                        // Wait for the user to be persisted
                        Thread.sleep(3000L);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    mailSender.sendConfirmationMail(user.getEmail(), user.getFirstName(),
                            mailTokenService.createTokenForUser(user));
                })
                        .start();
            } catch (Exception e) {
                throw new RuntimeException(String.format("Error while sending mail to %s", user.getEmail()));
            }
        }
    }

    private void deleteAssociatedTokens(String userId) {
        List<MailToken> mailTokens = mailTokenService.findAllByUserId(userId);
        mailTokens.forEach(mailToken -> mailTokenService.deleteToken(mailToken.getId()));
    }
}
