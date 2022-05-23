package com.sip.api.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sip.api.domains.role.Role;
import com.sip.api.exceptions.UnauthorizedException;
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
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    private final RoleService roleService;
    @Value("${jwt_secret}")
    private String secret;
    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.expirationDays}")
    private Long expirationDays;

    @Override
    public String issueAuthToken(User user) {
        try {
            String jwt = JWT.create()
                    .withSubject(user.getUsername())
                    .withIssuedAt(getIssuingDate())
                    .withExpiresAt(getExpirationDate())
                    .withIssuer(issuer)
//                    .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                    .withClaim("roles", getResourcesByRoleName(user).stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                    .sign(getSigningAlgorithm());
            log.info("New jot. Issuer {}. Algorithm {}. Token {}", issuer, getSigningAlgorithm(), jwt);
            return jwt;
        } catch (Exception e) {
            log.error("Error issuing auth token for user: {}. {}", user.getUsername(), e.getMessage());
            throw new UnauthorizedException("Error issuing auth token for user: " + user.getUsername() + ". " + e.getMessage());
        }
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

    /**
     * Here lays the magic:
     * Through the UserDetails interface we can get the user's roles since they're returned as a collection of GrantedAuthorities.
     * For every GrantedAuthority (aka Role) use the roleService to fetch the role from the database.
     * With fetched role, iterate over its resources and add them to the collection of GrantedAuthorities.
     *
     * @param userDetails Spring security interface that allows fetching the user Roles.
     * @return a list of GrantedAuthorities that represent the resources the user is allowed to get.
     */
    private Collection<GrantedAuthority> getResourcesByRoleName(UserDetails userDetails) {
        Collection<GrantedAuthority> rolesAndResources = new ArrayList<>();
        // Get user roles through UserDetails interface as Collection<GrantedAuthority> (collection of roles names)
        userDetails.getAuthorities().forEach(authority -> {
            // Get role by name
            Role role = roleService.findByName(authority.getAuthority());
            // For each role get its allowed resources and set them as granted authorities
            role.getAllowedResources().forEach(resource -> rolesAndResources.add(new SimpleGrantedAuthority(resource.getUrl())));
        });
        return rolesAndResources;
    }

    @Override
    public String getSubject(DecodedJWT token) {
        return token.getSubject();
    }

    @Override
    public Collection<GrantedAuthority> getAllowedResourcesAsGrantedAuthorities(DecodedJWT token) {
        return Stream.of(token.getClaim("roles")).map(resource -> new SimpleGrantedAuthority(resource.toString())).collect(Collectors.toList());
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
