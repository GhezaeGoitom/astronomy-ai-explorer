package com.gg.astronomy_ai_explorer.config;

import com.gg.astronomy_ai_explorer.component.auth.JwtAuthenticationFilter;
import com.gg.astronomy_ai_explorer.exception.CustomAccessDeniedHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .cors(cors -> {})

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )

                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(customAccessDeniedHandler)
                )

                .authorizeHttpRequests(auth -> auth
                        
//                                handling option request preflight from frontend
                                .requestMatchers(HttpMethod.OPTIONS, "/**")
                                .permitAll()

                                // Authentication
                                .requestMatchers("/api/auth/**")
                                .permitAll()

                                // Search
                                .requestMatchers("/api/search/**")
                                .hasAnyRole("GUEST", "SCIENTIST", "ADMIN")

                                // User management - ADMIN only
                                .requestMatchers("/api/users/**")
                                .hasRole("ADMIN")

                                // manage roles
                                .requestMatchers("/api/roles/**")
                                .hasRole("ADMIN")

                                // Person
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/persons/**"
                                ).hasAnyRole("SCIENTIST", "ADMIN")

                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/api/persons/**"
                                ).hasAnyRole("SCIENTIST", "ADMIN")

                                .requestMatchers(
                                        HttpMethod.PUT,
                                        "/api/persons/**"
                                ).hasAnyRole("SCIENTIST", "ADMIN")

                                .requestMatchers(
                                        HttpMethod.DELETE,
                                        "/api/persons/**"
                                ).hasRole("ADMIN")

                                // Discover
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/discoveries/**"
                                ).hasAnyRole("SCIENTIST", "ADMIN")

                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/api/discoveries/**"
                                ).hasAnyRole("SCIENTIST", "ADMIN")

                                .requestMatchers(
                                        HttpMethod.PUT,
                                        "/api/discoveries/**"
                                ).hasAnyRole("SCIENTIST", "ADMIN")

                                .requestMatchers(
                                        HttpMethod.DELETE,
                                        "/api/discoveries/**"
                                ).hasRole("ADMIN")

                                // Publish
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/publishes/**"
                                ).hasAnyRole("SCIENTIST", "ADMIN")

                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/api/publishes/**"
                                ).hasAnyRole( "ADMIN")

                                .requestMatchers(
                                        HttpMethod.PUT,
                                        "/api/publishes/**"
                                ).hasAnyRole("ADMIN")

                                .requestMatchers(
                                        HttpMethod.DELETE,
                                        "/api/publishes/**"
                                ).hasRole("ADMIN")

                                // Anyone with an astronomy role can READ astronomy data
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/planets/**",
                                        "/api/stars/**",
                                        "/api/galaxies/**",
                                        "/api/supernovae/**",
                                        "/api/black-holes/**",
                                        "/api/universes/**",
                                        "/api/galactic-clusters/**",
                                        "/api/galactic-superclusters/**",
                                        "/api/solar-systems/**"
                                ).hasAnyRole(
                                        "GUEST",
                                        "SCIENTIST",
                                        "ADMIN"
                                )

// Scientists and admins can CREATE
                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/api/planets/**",
                                        "/api/stars/**",
                                        "/api/galaxies/**",
                                        "/api/supernovae/**",
                                        "/api/black-holes/**",
                                        "/api/universes/**",
                                        "/api/galactic-clusters/**",
                                        "/api/galactic-superclusters/**",
                                        "/api/solar-systems/**"
                                ).hasAnyRole(
                                        "SCIENTIST",
                                        "ADMIN"
                                )

// Scientists and admins can UPDATE
                                .requestMatchers(
                                        HttpMethod.PUT,
                                        "/api/planets/**",
                                        "/api/stars/**",
                                        "/api/galaxies/**",
                                        "/api/supernovae/**",
                                        "/api/black-holes/**",
                                        "/api/universes/**",
                                        "/api/galactic-clusters/**",
                                        "/api/galactic-superclusters/**",
                                        "/api/solar-systems/**"
                                ).hasAnyRole(
                                        "SCIENTIST",
                                        "ADMIN"
                                )

// Only admins can DELETE
                                .requestMatchers(
                                        HttpMethod.DELETE,
                                        "/api/planets/**",
                                        "/api/stars/**",
                                        "/api/galaxies/**",
                                        "/api/supernovae/**",
                                        "/api/black-holes/**",
                                        "/api/universes/**",
                                        "/api/galactic-clusters/**",
                                        "/api/galactic-superclusters/**",
                                        "/api/solar-systems/**"
                                ).hasRole("ADMIN")
                );

        return http.build();
    }

}