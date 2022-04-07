package com.sip.api.security.config;

import com.sip.api.security.filters.AuthenticationFilter;
import com.sip.api.security.filters.AuthorizationFilter;
import com.sip.api.services.UserService;
import com.sip.api.utils.JwtHandlerUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService;
    private final JwtHandlerUtil jwtHandlerUtil;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .cors()
                .disable();

        http.sessionManagement().sessionCreationPolicy(STATELESS);

        // public endpoints
        http.authorizeRequests()
                .antMatchers("/webjars/springfox-swagger-ui/**",
                        "/v2/api-docs",
                        "/swagger-resources/**",
                        "/swagger-ui.html",
                        "/login/**", "/register/**", "/password/**")
                .permitAll();
        http
                .addFilter(new AuthenticationFilter(authenticationManager(), jwtHandlerUtil))
                .addFilterAfter(new AuthorizationFilter(userService, jwtHandlerUtil), AuthenticationFilter.class);

        http.authorizeRequests()
                .anyRequest()
                .authenticated();
    }
}
