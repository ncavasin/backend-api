package com.sip.api.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.stream.Collectors;

@Slf4j
@Data
@Component
@AllArgsConstructor
public class JwtFactory {
    @Value("${jwt-secret}")
    private String secret;

    @Value("${jwt-issuer}")
    private String issuer;

    @Value("${jwt-expiration-days}")
    private Long expiration;

    private Algorithm algorithm = Algorithm.HMAC512(secret.getBytes());

    public String issueAuthToken(User user, String issuer) {
        String jwt = String.format("Error issuing authentication token for user: %s. Message: %s", user.getUsername(), "Not implemented");
        try {
            jwt = JWT.create()
                    .withSubject(user.getUsername())
                    .withExpiresAt(Date.valueOf(LocalDate.now().plusDays(expiration)))
                    .withIssuer(issuer)
                    .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                    .sign(algorithm);
        } catch (Exception e) {
            log.error("Error issuing auth token for user: {}. {}", user.getUsername(), e.getMessage());
        }
        return jwt;
    }

    public static String issueRefreshToken(User user, String issuer) {
        String jwt = String.format("Error issuing refresh token for user: %s. Message: %s", user.getUsername(), "Not implemented");
        try {
            jwt = JWT.create()
                    .withSubject(user.getUsername())
                    .withExpiresAt(Date.valueOf(LocalDate.now().plusDays(expiration)))
                    .withIssuer(issuer)
                    .sign(algorithm);
        } catch (Exception e) {
            log.error("Error issuing refresh token for user: {}. {}", user.getUsername(), e.getMessage());
        }
        return jwt;
    }

    public static DecodedJWT decodeToken(String rawToken) {
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            return verifier.verify(rawToken);
        } catch (Exception e) {
            log.error("Error decoding token: {}. {}", rawToken, e.getMessage());
        }
        return null;
    }

    public String getSubject(DecodedJWT token) {
        return token.getSubject();
    }

    public String getIssuer(DecodedJWT token) {
        return token.getIssuer();
    }

}
