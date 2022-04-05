package com.sip.api.security.filters;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.sip.api.utils.JwtFactory;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        // Skip login
        if (!request.getServletPath().equals("/login") || !request.getServletPath().contains("swagger")) {
            String authHeader = request.getHeader("Authorization");
            try {
                // Skip if token is missing or isn't a jwt bearer
                if (authHeader != null & authHeader.startsWith("Bearer ")) {
                    DecodedJWT decodedToken = JwtFactory.decodeToken(authHeader.replace("Bearer ", ""));
                    // Skip if token decoding failed
                    if (decodedToken != null) {
                        String username = decodedToken.getSubject();
                        String[] roles = decodedToken.getClaim("roles").asArray(String.class);

                        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                        Stream.of(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));

                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } catch (Exception e) {
                log.error("Error while verifying JWT token. Error: {}.", e.getMessage());
                e.printStackTrace();
            }
            filterChain.doFilter(request, response);
        }
    }
}