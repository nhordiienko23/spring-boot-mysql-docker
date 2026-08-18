package com.github.nhordiienko23.springmysql.config;

import com.github.nhordiienko23.springmysql.jwtToken.JwtFilter;
import com.github.nhordiienko23.springmysql.service.CustomOAuth2UserService;
import com.github.nhordiienko23.springmysql.service.CustomOidcUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   CustomOAuth2UserService customOAuth2UserService,
                                                   CustomOidcUserService customOidcUserService) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/register", "/login", "/error").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form.permitAll()
                        .defaultSuccessUrl("/swagger-ui/index.html", true))
                .oauth2Login(oauth2 -> oauth2
                        .defaultSuccessUrl("/swagger-ui/index.html", true)
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)

                                .oidcUserService(customOidcUserService)
                        )
                )
                .csrf(csrf -> csrf.disable())
                .build();
    }
}