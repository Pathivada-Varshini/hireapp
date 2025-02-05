package com.hireportal.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true) // Ensuring method-level security is enabled
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/api/users/register",
                                "/api/users/login"
                        )
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html"
                        ).permitAll() // Allow access to Swagger UI and API docs
                        .requestMatchers("/api/users/register", "/api/users/login").permitAll() // Public registration and login
                        .requestMatchers("/api/applications/{jobId}/apply").hasAuthority("JOB_SEEKER") // JOB_SEEKER authority required for applications
                        .requestMatchers("/api/applications/{id}/status").hasAuthority("JOB_PROVIDER") // JOB_PROVIDER authority for application status
                        .requestMatchers("/api/jobs/create", "/api/jobs/{jobId}", "/api/categories/**", "/api/employers/**").hasAuthority("JOB_PROVIDER") // JOB_PROVIDER authority required for job management
                        .requestMatchers("/api/summaries/**", "/api/skills/**", "/api/experiences/**", "/api/languages/**", "/api/education/**").hasAuthority("JOB_SEEKER") // JOB_SEEKER authority for managing personal details
                        .requestMatchers("/api/applications/**", "/api/jobs/**").hasAnyAuthority("JOB_PROVIDER", "JOB_SEEKER") // Both authorities for application and job details
                        .anyRequest().authenticated() // Ensure all other requests are authenticated
                )
                .httpBasic(Customizer.withDefaults()); // Basic authentication (adjust if using JWT or another mechanism)

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
