package com.sip.api.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.stream.Collectors;

@Data
@Builder
@Component
@AllArgsConstructor
public class JWTIssuer {
    // TODO: change this!
//    @Value("JWTSecretKey")
    private static String secret = "secret";

//    @Value("${JWTExpirationDays}")
    private static Long expiration = 5L;

    private static Algorithm algorithm = Algorithm.HMAC512(secret.getBytes());

    public static String issueAuthToken(User user, String issuer) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(Date.valueOf(LocalDate.now().plusDays(expiration)))
                .withIssuer(issuer)
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
    }

    public static String issueRefreshToken(User user, String issuer) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(Date.valueOf(LocalDate.now().plusDays(expiration)))
                .withIssuer(issuer)
                .sign(algorithm);
    }
}
