package com.example.main.configuration;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    private static final String[] SWAGGER_WHITELIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/swagger-ui/index.html",
            "/swagger-resources/**",
            "/webjars/**"
    };
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
//        security.csrf(c -> c.disable()).cors(c -> c.disable());
//       security.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//        security.addFilterBefore(jwtFilter , AuthorizationFilter.class);
//        security.authorizeHttpRequests(auth -> auth
//                .requestMatchers("/api/v1/admin/create", "/api/v1/admin/login").permitAll()
////                .requestMatchers(SWAGGER_WHITELIST).permitAll()
//                .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
//                .requestMatchers("/api/villages/info/create").hasRole("ADMIN")
//                .anyRequest().authenticated()
//        );
////                .httpBasic(Customizer.withDefaults());
//
//
//
////                .formLogin(login -> login
////                        .loginProcessingUrl("/api/v1/admin/login") // same login API
////                        .permitAll()
////                )
////                .logout(logout -> logout
////                        .logoutUrl("/api/v1/admin/logout") // 👈 ye hi logout URL tu frontend se hit karega
////                        .invalidateHttpSession(true)      // session destroy
////                        .deleteCookies("JSESSIONID")       // cookie delete
////                        .logoutSuccessHandler((request, response, authentication) -> {
////                            response.setStatus(HttpServletResponse.SC_OK);
////                            response.getWriter().write("{\"message\": \"Logout successful\"}");
////                        })
////                        .permitAll()
////                ).httpBasic(http->http.disable());
//        return security.build();
//    }
//@Bean
//public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
//    security.csrf(c -> c.disable())
//            .cors(c -> c.disable())
//            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//            .addFilterBefore(jwtFilter, AuthorizationFilter.class)
//            .authorizeHttpRequests(auth -> auth
//                    .requestMatchers("/api/v1/admin/create", "/api/v1/admin/login").permitAll()
//                    .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
//                    .requestMatchers("/api/villages/info/create").hasRole("ADMIN")
//                    .anyRequest().authenticated()
//            );
//    return security.build();
//}

   @Bean
public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
    security
        .csrf(c -> c.disable())
        .cors(c -> c.disable())
        .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
        .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/api/v1/admin/create",
                        "/api/v1/admin/login",
                        "/api/v1/admin/logout"   // ✅ logout allow
                ).permitAll()
                .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/villages/info/create").hasRole("ADMIN")
                .anyRequest().authenticated()
        );

    return security.build();
}



}
