package com.clinic.vet_clinic.config;

import com.clinic.vet_clinic.auth.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService; // Adicione o UserDetailsService aqui

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        // 1. Endpoints públicos (Login, Registro, Swagger)
                        .requestMatchers(
                                "/auth/**",
                                "/users/register",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // 2. Permissões de ADMIN
                        .requestMatchers("/users/**", "/consultas/**", "/veterinary/**", "/clinic/**", "/reports/**").hasRole("ADMIN")

                        // 3. Permissões de USUÁRIO (ex: gerenciar seus próprios pets)
                        .requestMatchers("/pets/**").hasAnyRole("USER", "ADMIN")

                        // 4. Qualquer outra requisição precisa estar autenticada
                        .anyRequest().authenticated()
                )
                // Configura a política de sessão para STATELESS (sem estado)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Define o provedor de autenticação que criamos abaixo
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // --- BEANS ESSENCIAIS ---

    /**
     * ESTE É O BEAN QUE ESTAVA FALTANDO.
     * Ele cria o provedor de autenticação que usa nosso CustomUserDetailsService
     * e o nosso PasswordEncoder para validar as credenciais do usuário.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService); // Informa como buscar o usuário
        authProvider.setPasswordEncoder(passwordEncoder());   // Informa como verificar a senha
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}