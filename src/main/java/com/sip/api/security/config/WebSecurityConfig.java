package com.sip.api.security.config;

import com.sip.api.security.filters.JwtUsernamePasswordAuthenticationFilter;
import com.sip.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()//.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .disable()
                .cors().disable()
                .addFilter(new JwtUsernamePasswordAuthenticationFilter(authenticationManager()))
                .authorizeRequests()
                .antMatchers("/register/**", "/login/**", "/logout/**", "/management/**", "/users/**")
                .permitAll()
                .anyRequest()
                .authenticated();
//                .and()
//                .formLogin()
//                    .defaultSuccessUrl("/users", true)
//                    .usernameParameter("username") // must match form field name
//                    .passwordParameter("password") // must match form field name
//                    .and()
//                    .rememberMe()
//                        .rememberMeParameter("remember-me") // must match form field name!
//                        .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(7))
//                        .key(UUID.randomUUID().toString())
//                .and()
//                .logout()
//                    .logoutUrl("/logout")
//                    .clearAuthentication(true)
//                    .invalidateHttpSession(true)
//                    .deleteCookies("JSESSIONID", "remember-me")
//                    .logoutSuccessUrl("/login");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userService);
        return daoAuthenticationProvider;
    }
}

