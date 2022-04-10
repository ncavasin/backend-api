package com.sip.api.security.config;

import com.sip.api.security.filters.AuthenticationFilter;
import com.sip.api.security.filters.AuthorizationFilter;
import com.sip.api.services.UserService;
import com.sip.api.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService;
    private final JwtService jwtService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
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
                .addFilter(new AuthenticationFilter(authenticationManager(), jwtService))
                .addFilterAfter(new AuthorizationFilter(userService, jwtService), AuthenticationFilter.class);

        http.authorizeRequests()
                .anyRequest()
                .authenticated();

    }

    // Used by spring security if CORS is enabled.
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
