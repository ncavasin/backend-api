package com.sip.api.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtHandler {
    @Value("${jwt-secret}")
    private String secret;

    @Value("${jwt-issuer}")
    private String issuer;

    @Value("${jwt-expiration-days}")
    private Long expirationDays;

    public String issueAuthToken(User user) {
        String jwt = String.format("Error issuing authentication token for user: %s. Message: %s", user.getUsername(), "Not implemented");
        try {
            jwt = JWT.create()
                    .withSubject(user.getUsername())
                    .withIssuedAt(getIssuingDate())
                    .withExpiresAt(getExpirationDate())
                    .withIssuer(issuer)
                    .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                    .sign(getSigningAlgorithm());
            log.info("New jot. Issuer {}. Algorithm {}. Token {}", issuer, getSigningAlgorithm(), jwt);
        } catch (Exception e) {
            log.error("Error issuing auth token for user: {}. {}", user.getUsername(), e.getMessage());
        }
        return jwt;
    }

    public String issueRefreshToken(User user, String issuer) {
        String jwt = String.format("Error issuing refresh token for user: %s. Message: %s", user.getUsername(), "Not implemented");
        try {
            jwt = JWT.create()
                    .withSubject(user.getUsername())
                    .withIssuedAt(getIssuingDate())
                    .withExpiresAt(getExpirationDate())
                    .withIssuer(issuer)
                    .sign(getSigningAlgorithm());
        } catch (Exception e) {
            log.error("Error issuing refresh token for user: {}. {}", user.getUsername(), e.getMessage());
        }
        return jwt;
    }

    public DecodedJWT decodeToken(String rawToken) {
        try {
            JWTVerifier verifier = JWT.require(getSigningAlgorithm()).build();
            return verifier.verify(rawToken);
        } catch (Exception e) {
            log.error("Error decoding invalid token: {}", e.getMessage());
        }
        return null;
    }

    public String getSubject(DecodedJWT token) {
        return token.getSubject();
    }

    public boolean isExpired(DecodedJWT token) {
        return token.getExpiresAt().before(Timestamp.valueOf(LocalDateTime.now()));
    }

    public boolean issuerIsNotValid(DecodedJWT decodedToken) {
        return !decodedToken.getIssuer().equals(issuer);
    }

    private Timestamp getIssuingDate() {
        return Timestamp.valueOf(LocalDateTime.now());
    }

    private Timestamp getExpirationDate() {
        return Timestamp.valueOf(LocalDateTime.now().plusDays(expirationDays));
    }

    private Algorithm getSigningAlgorithm() {
        return Algorithm.HMAC512(secret.getBytes());
    }
}
