package com.sip.api.security.config;

import com.sip.api.security.filters.AuthenticationFilter;
import com.sip.api.security.filters.AuthorizationFilter;
import com.sip.api.services.UserService;
import com.sip.api.utils.JwtHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService;
    private final JwtHandler jwtHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable();
//
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.setAllowedHeaders(
//                List.of("Authorization", "Cache-Control", "Content-Type", "X-PT-SESSION-ID", "NGSW-BYPASS"));
//        corsConfiguration.setAllowedOrigins(List.of("*"));
//        corsConfiguration
//                .setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PUT", "OPTIONS", "PATCH", "DELETE"));
//        corsConfiguration.setAllowCredentials(true);
//        corsConfiguration.setExposedHeaders(List.of("Authorization"));

        http.sessionManagement().sessionCreationPolicy(STATELESS);

        // public endpoints
        http.authorizeRequests()
                .antMatchers("/webjars/springfox-swagger-ui/**",
                        "/v2/api-docs",
                        "/swagger-resources/**",
                        "/swagger-ui.html",
                        "/login/**", "/register/**", "/password/**")
                .permitAll()
                .anyRequest()
                .authenticated();
//                .and()
//                .cors().configurationSource(request -> corsConfiguration);


        http
                .addFilter(new AuthenticationFilter(authenticationManager(), jwtHandler))
                .addFilterAfter(new AuthorizationFilter(userService, jwtHandler), AuthenticationFilter.class);
//
//        http.authorizeRequests()
//                .anyRequest()
//                .authenticated();
    }


    // Overrides default CorsConfiguration
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
