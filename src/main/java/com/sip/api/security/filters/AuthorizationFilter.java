package com.sip.api.security.filters;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.base.Strings;
import com.sip.api.utils.JwtFactory;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        // Skip login
        if( request.getServletPath().equals("/login")) filterChain.doFilter(request, response);

        String authHeader = request.getHeader("Authorization");

        // Skip if not present
        if (Strings.isNullOrEmpty(authHeader) | !authHeader.startsWith("Bearer ")) filterChain.doFilter(request, response);

        DecodedJWT decodedToken = JwtFactory.decodeToken(authHeader.replace("Bearer ", ""));
        try {
            Jws<Claims> jwsClaims = Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor(key.getBytes()))
                    .parseClaimsJws(jwtToken);

            Claims body = jwsClaims.getBody();
            String username = body.getSubject();
            body.get("authorities");

        } catch (Exception e) {
            log.error("Error while verifying JWT token. Error: {}.", e.getMessage());
            e.printStackTrace();
        }

    }
}
