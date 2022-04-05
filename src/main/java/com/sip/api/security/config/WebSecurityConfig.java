package com.sip.api.security.config;

import com.sip.api.security.PasswordEncoder;
import com.sip.api.security.filters.AuthenticationFilter;
import com.sip.api.security.filters.AuthorizationFilter;
import com.sip.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(STATELESS);

        http
            .csrf()//.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .disable()
            .cors().disable()
            .sessionManagement()
                .sessionCreationPolicy(STATELESS)
            .and()
            .authorizeRequests()
                .antMatchers("/webjars/springfox-swagger-ui/**",
                        "/v2/api-docs",
                        "/swagger-resources/**",
                        "/swagger-ui.html")
                .permitAll()
            .and()
            .addFilter(new AuthenticationFilter(authenticationManager()))
            .addFilterBefore(new AuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
            .authorizeRequests()
                .antMatchers("/register/**", "/login/**", "/logout/**", "/management/**")//, "/users/**")
                .permitAll()
            .anyRequest().authenticated();
//            .and()
//            .formLogin()
//                .defaultSuccessUrl("/users", true)
//                .usernameParameter("username") // must match form field name
//                .passwordParameter("password") // must match form field name
//            .and()
//                .rememberMe()
//                    .rememberMeParameter("remember-me") // must match form field name!
//                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(7))
//                    .key(UUID.randomUUID().toString())
//            .and()
//                .logout()
//                    .logoutUrl("/logout")
//                    .clearAuthentication(true)
//                    .invalidateHttpSession(true)
//                    .deleteCookies("JSESSIONID", "remember-me")
//                    .logoutSuccessUrl("/login");
    }

//    @Bean
//    @Override
//    // This allows us to inject an AuthenticationManager in CustomAuthFilter
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) {
//        auth.authenticationProvider(daoAuthenticationProvider());
//    }
//
//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider() {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder.encoder());
//        daoAuthenticationProvider.setUserDetailsService(userService);
//        return daoAuthenticationProvider;
//    }
}
