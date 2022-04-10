package com.sip.api.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sip.api.domains.role.Role;
import com.sip.api.services.JwtService;
import com.sip.api.services.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    private final RoleService roleService;
    @Value("${jwt-secret}")
    private String secret;
    @Value("${jwt-issuer}")
    private String issuer;
    @Value("${jwt-expiration-days}")
    private Long expirationDays;

    @Override
    public String issueAuthToken(User user) {
        String jwt = String.format("Error issuing authentication token for user: %s. Message: %s", user.getUsername(), "Not implemented");
        try {
            jwt = JWT.create()
                    .withSubject(user.getUsername())
                    .withIssuedAt(getIssuingDate())
                    .withExpiresAt(getExpirationDate())
                    .withIssuer(issuer)
//                    .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                    .withClaim("roles", getResourcesByRoleName(user))
                    .sign(getSigningAlgorithm());
            log.info("New jot. Issuer {}. Algorithm {}. Token {}", issuer, getSigningAlgorithm(), jwt);
        } catch (Exception e) {
            log.error("Error issuing auth token for user: {}. {}", user.getUsername(), e.getMessage());
        }
        return jwt;
    }

    private List<SimpleGrantedAuthority> getResourcesByRoleName(UserDetails userDetails){
        List<SimpleGrantedAuthority> rolesAndResources = new ArrayList<>();

        // Get user roles through UserDetails interface as Collection<GrantedAuthority> (collection of roles names)
        userDetails.getAuthorities().forEach(authority -> {
            // TODO: FINISH THIS!!!!
            // Get role by name
            Role role = roleService.findByName(authority.getAuthority());
            // For each role get its allowed resources
            role.getAllowedResources().forEach(resource -> {
                // For each resource parse it as a String
//                String prueba = resource.getName() + "|" + resource.getUrl() + "|" ;
                // And add it to the list of roles and resources
                rolesAndResources.add(new SimpleGrantedAuthority(resource.toString()));
                // IT WORKS BUT I NEED TO IMPLEMENT THE CREATION OF THE RESOURCE ASSOCIATED TO A ROLE
            });
        });
        return rolesAndResources;
    }

    @Override
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

    @Override
    public DecodedJWT decodeToken(String rawToken) {
        try {
            JWTVerifier verifier = JWT.require(getSigningAlgorithm()).build();
            return verifier.verify(rawToken);
        } catch (Exception e) {
            log.error("Error decoding invalid token: {}", e.getMessage());
        }
        return null;
    }

    @Override
    public String getSubject(DecodedJWT token) {
        return token.getSubject();
    }

    @Override
    public boolean isExpired(DecodedJWT token) {
        return token.getExpiresAt().before(Timestamp.valueOf(LocalDateTime.now()));
    }

    @Override
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
