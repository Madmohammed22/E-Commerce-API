package com.ecommerce.config;

import com.ecommerce.security.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.configuration.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthFilter) throws Exception {
        // Security configuration constants
        final String AUTH_API_PATTERN = "/api/auth/**";
        final String SWAGGER_DOCS_PATTERN = "/v3/api-docs/**";
        final String SWAGGER_UI_PATTERN = "/swagger-ui/**";
        final String SWAGGER_UI_HTML = "/swagger-ui.html";
        final String SWAGGER_API_DOCS = "/v3/api-docs";
        final String SWAGGER_RESOURCES = "/swagger-resources/**";
        final String SWAGGER_CONFIG = "/swagger-ui-custom.html";
        final String SWAGGER_WEBJARS = "/webjars/**";
        final String PRODUCT_SEARCH_PATTERN = "/api/products/search/**";
        final String PRODUCTS_PATTERN = "/api/products/**";  // Add this line
        final String ADMIN_API_PATTERN = "/api/admin/**";
        final String ADMIN_ROLE = "ADMIN";

        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(AUTH_API_PATTERN).permitAll()
                        .requestMatchers(SWAGGER_DOCS_PATTERN, SWAGGER_UI_PATTERN, SWAGGER_UI_HTML, 
                                   SWAGGER_API_DOCS, SWAGGER_RESOURCES, SWAGGER_CONFIG, SWAGGER_WEBJARS).permitAll()
                        .requestMatchers(PRODUCT_SEARCH_PATTERN).permitAll()
                        .requestMatchers(PRODUCTS_PATTERN).permitAll()  // Add this line
                        .requestMatchers(ADMIN_API_PATTERN).hasRole(ADMIN_ROLE)
                        .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
}

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("test@example.com")
                .password(passwordEncoder().encode("password"))
                .authorities("ROLE_USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }
}