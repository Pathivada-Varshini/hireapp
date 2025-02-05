package com.hireportal.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Safe for stateless APIs, warning may appear
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Ensures no sessions are created
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                        .requestMatchers("/api/users/register", "/api/users/login").permitAll()
                        .requestMatchers("/api/applications/{jobId}/apply").hasAuthority("JOB_SEEKER")
                        .requestMatchers("/api/applications/{id}/status").hasAuthority("JOB_PROVIDER")
                        .requestMatchers("/api/jobs/create", "/api/jobs/{jobId}", "/api/categories/**", "/api/employers/**").hasAuthority("JOB_PROVIDER")
                        .requestMatchers("/api/summaries/**", "/api/skills/**", "/api/experiences/**", "/api/languages/**", "/api/education/**").hasAuthority("JOB_SEEKER")
                        .requestMatchers("/api/applications/**", "/api/jobs/**").hasAnyAuthority("JOB_PROVIDER", "JOB_SEEKER")
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults()); // Updated method for Spring Security 6.1

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
