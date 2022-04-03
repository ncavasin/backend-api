package com.sip.api.security.filters;

import com.google.common.base.Strings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenVerifier extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Fetch auth header
        String authHeader = request.getHeader("Authorization");

        if (Strings.isNullOrEmpty(authHeader) | !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String key = "sip-api-key";
        try{
            String jwtToken = authHeader.replace("Bearer ", "");
            Jws<Claims> jwsClaims = Jwts.parser()
                    .setSigningKey(SignatureAlgorithm.HS512, Keys.hmacShaKeyFor(key.getBytes()))
                    .parseClaimsJws(jwtToken);

            Claims body = jwsClaims.getBody();
            String username = body.getSubject();
            body.get("authorities");

        }catch (Exception e){

        }

    }
}
