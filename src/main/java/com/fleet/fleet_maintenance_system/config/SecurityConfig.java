package com.fleet.fleet_maintenance_system.config;

import com.fleet.fleet_maintenance_system.security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/maintenances/**").hasAnyRole("ADMIN", "TECHNICIAN")
                        .requestMatchers("/api/reports/**").hasAnyRole("ADMIN", "MANAGER")

                        .requestMatchers(HttpMethod.GET, "/api/vehicles/**").hasAnyRole("ADMIN", "MANAGER", "TECHNICIAN")
                        .requestMatchers(HttpMethod.GET, "/api/parts/**").hasAnyRole("ADMIN", "MANAGER", "TECHNICIAN")
                        .requestMatchers(HttpMethod.GET, "/api/suppliers/**").hasAnyRole("ADMIN", "MANAGER", "TECHNICIAN")
                        .requestMatchers(HttpMethod.GET, "/api/technicians/**").hasAnyRole("ADMIN", "MANAGER")

                        .requestMatchers(HttpMethod.POST, "/api/vehicles/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/parts/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/suppliers/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/technicians/**").hasAnyRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/api/vehicles/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/parts/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/suppliers/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/technicians/**").hasAnyRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/api/vehicles/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/parts/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/suppliers/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/technicians/**").hasAnyRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
