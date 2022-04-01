package com.sip.api.services.impl;

import com.sip.api.domains.registration.MailToken;
import com.sip.api.domains.user.User;
import com.sip.api.dtos.TokenDto;
import com.sip.api.exceptions.BadRequestException;
import com.sip.api.exceptions.NotFoundException;
import com.sip.api.repositories.MailTokenRepository;
import com.sip.api.services.MailTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailTokenServiceImpl implements MailTokenService {
    private final MailTokenRepository mailTokenRepository;

    @Override
    public TokenDto createTokenForUser(User user) {
        MailToken mailToken = MailToken.builder()
                .token(UUID.randomUUID().toString())
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .expiresAt(Timestamp.valueOf(LocalDateTime.now().plusMinutes(15L)))
                .user(user)
                .build();
        mailTokenRepository.save(mailToken);
        return new TokenDto(mailToken.getToken());
    }

    @Override
    public MailToken findByToken(String token) {
        return mailTokenRepository.findByToken(token).orElseThrow(() -> new NotFoundException("Token not found"));
    }

    @Override
    public void confirmToken(String token) {
        MailToken mailToken = mailTokenRepository.findByToken(token).orElseThrow(() -> new NotFoundException("Token not found"));
        mailToken.setConfirmedAt(checkMailToken(mailToken));
        mailTokenRepository.save(mailToken);
    }

    private Timestamp checkMailToken(MailToken mailToken){
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        if (mailToken.getConfirmedAt() != null) throw new BadRequestException("Token already confirmed");
        if (mailToken.getExpiresAt().before(now)) throw new BadRequestException("Token expired");
        return now;
    }
}
