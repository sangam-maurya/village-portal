package com.example.main.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        security.csrf(c -> c.disable()).cors(c -> c.disable());
        security.authorizeHttpRequests(auth -> auth.requestMatchers("/api/v1/admin/create", "/api/v1/admin/login").permitAll()
                .requestMatchers("/api/v1/admin/**").hasRole("ADMIN").anyRequest().authenticated());
        security.formLogin(login->login.permitAll())
                .httpBasic(org.springframework.security.config.Customizer.withDefaults());
        return security.build();
    }
}
