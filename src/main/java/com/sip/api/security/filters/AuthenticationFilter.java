package com.sip.api.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sip.api.dtos.UserCredentialsDto;
import com.sip.api.exceptions.UnauthorizedException;
import com.sip.api.utils.JwtFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        User user = (User) authentication.getPrincipal();

        String accessToken = JwtFactory.issueAuthToken(user, request.getRequestURL().toString());
        String refreshToken = JwtFactory.issueRefreshToken(user, request.getRequestURL().toString());

        response.addHeader("Authorization", "Bearer " + accessToken);
        response.addHeader("Authorization-Refresh", refreshToken);
    }
}
