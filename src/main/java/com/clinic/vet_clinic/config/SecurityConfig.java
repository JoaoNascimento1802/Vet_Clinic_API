package com.clinic.vet_clinic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.HttpMethod; // Import HttpMethod
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        // 1. Endpoints públicos (Swagger, etc.) - SEMPRE OS PRIMEIROS
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                        // 2. Regra para permitir a criação do PRIMEIRO ADMIN (apenas o POST)
                        // Se você já criou o admin, pode COMENTAR ou REMOVER esta linha

                        // 3. Regras para ADMIN - DEVEM VIR ANTES DE anyRequest().authenticated()
                        // Garanta que estas linhas estejam presentes e CORRETAS
                        .requestMatchers("/users/**").hasRole("ADMIN") // <-- Resto de /users exige ADMIN
                        .requestMatchers("/consultas/**").hasRole("ADMIN")
                        .requestMatchers("/veterinary/**").hasRole("ADMIN")
                        .requestMatchers("/clinic/**").hasRole("ADMIN")

                        // 4. Regra para endpoints que exigem QUALQUER usuário autenticado (como /pets)
                        // Esta regra captura todo o resto, então DEVE SER A ÚLTIMA DENTRE AS ESPECÍFICAS
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}