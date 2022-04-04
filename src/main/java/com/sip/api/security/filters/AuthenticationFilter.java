package com.sip.api.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sip.api.dtos.UserCredentialsDto;
import com.sip.api.exceptions.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.stream.Collectors;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // Get request body and parse it
            UserCredentialsDto authenticationRequest = new ObjectMapper().readValue(request.getInputStream(), UserCredentialsDto.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword());
            return authenticationManager.authenticate(authentication);
        } catch (IOException e) {
            log.error("User tried to authenticate with wrong credentials. Error: {}", e.getMessage());
            throw new UnauthorizedException("Bad credentials!");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authentication) {
        // TODO: replace with env key
        String key = "secret";

        User user = (User) authentication.getPrincipal();

        Algorithm algorithm = Algorithm.HMAC512(key.getBytes());

        String accessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(Date.valueOf(LocalDate.now().plusWeeks(2)))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        String refreshToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(Date.valueOf(LocalDate.now().plusWeeks(4)))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);

        response.addHeader("Authorization", "Bearer " + accessToken);

        response.addHeader("Authorization-Refresh", refreshToken);
//        chain.doFilter(request, response);


//        String jwtToken = Jwts.builder()
//                .setIssuer("Nicolas_Cavasin_for_SIP_2022")
//                .setSubject(authentication.getName())
//                .setIssuedAt(new java.util.Date())
//                .setNotBefore(new java.util.Date())
//                .setExpiration(Date.valueOf(LocalDate.now().plusWeeks(2)))
//                .signWith(SignatureAlgorithm.HS512, Keys.hmacShaKeyFor(key.getBytes()))
//                .compact();
//        response.addHeader("Authorization", "Bearer " + jwtToken);
    }
}
