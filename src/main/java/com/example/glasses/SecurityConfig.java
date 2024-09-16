package com.example.glasses;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Вимкнути CSRF-захист
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()  // Дозволити доступ до всіх запитів без аутентифікації
                );
        return http.build();
    }
}