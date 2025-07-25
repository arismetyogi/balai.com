package com.balai.inventory.config;

import com.balai.inventory.filter.JwtAuthenticationFilter;
import com.balai.inventory.service.UserValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(UserValidationService userValidationService) {
        return new JwtAuthenticationFilter(userValidationService);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, UserValidationService userValidationService) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/products", "/api/products/**").authenticated()
                        .anyRequest().permitAll()
                );

        http.addFilterBefore(jwtAuthenticationFilter(userValidationService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
