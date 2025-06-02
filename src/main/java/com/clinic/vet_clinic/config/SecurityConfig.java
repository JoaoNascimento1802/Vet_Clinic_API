package com.clinic.vet_clinic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Desabilita CSRF para APIs REST
                .authorizeHttpRequests(authorize -> authorize
                        // Endpoints públicos (swagger, etc.)
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                        // Apenas ADMIN pode ver todos os usuários e criar novos
                        .requestMatchers("/users/**").hasRole("ADMIN")
                        // Apenas ADMIN pode ver todas as consultas e criar novas
                        .requestMatchers("/consultas/**").hasRole("ADMIN")
                        // Apenas ADMIN pode ver todos os veterinários e criar novos
                        .requestMatchers("/veterinary/**").hasRole("ADMIN")
                        // Permite acesso a outros endpoints como pets e clínicas para todos os usuários autenticados
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults()); // Usa autenticação HTTP Basic (para simplificar, em produção use OAuth2/JWT)
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}