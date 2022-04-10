package com.sip.api.services;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public interface JwtService {
    String secret = "secret";
    String issuer = "issuer";
    Long expirationDays = 1L;

    String issueAuthToken(User user);

    String issueRefreshToken(User user, String issuer);

    DecodedJWT decodeToken(String rawToken);

    String getSubject(DecodedJWT token);

    Collection<GrantedAuthority> getAllowedResourcesAsGrantedAuthorities(DecodedJWT token);

    boolean isExpired(DecodedJWT token);

    boolean issuerIsNotValid(DecodedJWT decodedToken);
}
