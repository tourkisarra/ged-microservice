package com.example.ged_microservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .antMatchers(
                        //  Swagger
                        "/v3/api-docs/**",
                        "/configuration/**",
                        "/swagger-ui/**",
                        "/swagger-resources/**",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/api-docs/**",
                        //  OPTIONS pour CORS
                        "/**.html",
                        "/**.css",
                        "/**.js"
                );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeRequests(auth -> auth
                        //  Toutes les routes sécurisées nécessitent juste un JWT valide
                        .antMatchers("/api/**").authenticated()
                        .anyRequest().permitAll()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt() // pas besoin de jwtAuthConverter ici si pas de rôle à gérer
                )
                .build();
    }
}
