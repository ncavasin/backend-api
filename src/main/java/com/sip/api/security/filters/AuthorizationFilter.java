package com.sip.api.security.filters;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.base.Strings;
import com.sip.api.domains.user.User;
import com.sip.api.dtos.user.UserEmailDto;
import com.sip.api.services.UserService;
import com.sip.api.utils.JwtHandler;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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

@Slf4j
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {
    private final String BEARER_AND_SPACE = "Bearer ";
    private final UserService userService;
    private final JwtHandler jwtHandler;

    /*
     * This filter extracts the token from the request and validates its roles & permissions.
     * User authentication is managed in a previous filter.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // If the request does not contain an authorization header, it won't be authorized but must still traverse the filter chain
        // because it might try to access public resources.
        if (Strings.isNullOrEmpty(authHeader) || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Try to decode token
        DecodedJWT decodedToken = jwtHandler.decodeToken(authHeader.replace(BEARER_AND_SPACE, ""));

        // If decoding fails, it means the token is invalid
        if (decodedToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // If token is expired, won't be authorized but must still traverse the filter chain because it might try
        // to access public resources.
        if (jwtHandler.isExpired(decodedToken)) {
            log.warn("JWT expired at {}.", decodedToken.getExpiresAt().toString());
            filterChain.doFilter(request, response);
            return;
        }

        // If token issuer is not us, won't be authorized but must still traverse the filter chain because it might try
        // to access public resources.
        if (jwtHandler.issuerIsNotValid(decodedToken)) {
            log.warn("Invalid JWT issuer. Who is {}?!", decodedToken.getIssuer());
            filterChain.doFilter(request, response);
            return;
        }

        // Decoding succeeded. Now fetch user from database using the email received in the token
        String username = jwtHandler.getSubject(decodedToken);
        User user = userService.findByEmail(UserEmailDto.builder().email(username).build());

        // Get roles from the token and convert them to Spring Security authorities
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));

        // Finally, verify that the user has the resource permission to access the url
        String requestingUrl = request.getRequestURL().toString();
        HttpMethod requestingMethod = HttpMethod.valueOf(request.getMethod());

        // TODO: add query to fetch allowed resources

        // Now return the roles the user has as authorities
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}