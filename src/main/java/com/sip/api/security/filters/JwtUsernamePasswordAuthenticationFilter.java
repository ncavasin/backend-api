package com.sip.api.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sip.api.dtos.UserCredentialsDto;
import com.sip.api.exceptions.UnauthorizedException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class JwtUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            UserCredentialsDto userCredentialsDto = new ObjectMapper().readValue(request.getInputStream(), UserCredentialsDto.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(userCredentialsDto.getEmail(), userCredentialsDto.getPassword());
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
                                            Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        // TODO: replace with env key
        String key = "sip-api-key";

        String jwtToken = Jwts.builder()
                .setIssuer("Nicolas_Cavasin_for_SIP_2022")
                .setSubject(authResult.getName())
                .setIssuedAt(new java.util.Date())
                .setNotBefore(new java.util.Date())
                .setExpiration(Date.valueOf(LocalDate.now().plusWeeks(2)))
                .signWith(SignatureAlgorithm.HS512, Keys.hmacShaKeyFor(key.getBytes()))
                .compact();
        response.addHeader("Authorization", "Bearer " + jwtToken);
    }
}
