package com.clinic.vet_clinic.config;

import com.clinic.vet_clinic.auth.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        // 1. Endpoints Públicos (não exigem login)
                        .requestMatchers("/auth/**", "/users/register", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                        // 2. Endpoints para Usuários Autenticados (USER e ADMIN)
                        .requestMatchers(HttpMethod.GET, "/clinic/**").authenticated()       // Usuários podem VER clínicas e detalhes
                        .requestMatchers(HttpMethod.GET, "/veterinary/**").authenticated()  // << CORREÇÃO PRINCIPAL: Usuários podem VER veterinários
                        .requestMatchers(HttpMethod.GET, "/consultas/my-consultations").authenticated() // Usuários podem VER suas consultas
                        .requestMatchers(HttpMethod.POST, "/consultas").authenticated()      // Usuários podem CRIAR consultas
                        .requestMatchers("/pets/**").authenticated()                         // Usuários podem gerenciar seus próprios pets

                        // 3. Endpoints Exclusivos para ADMIN
                        // (Qualquer outra ação em /clinic, /veterinary, /consultas que não seja as permitidas acima)
                        .requestMatchers("/clinic/**").hasRole("ADMIN")
                        .requestMatchers("/veterinary/**").hasRole("ADMIN")
                        .requestMatchers("/consultas/**").hasRole("ADMIN")

                        // Outras áreas de admin
                        .requestMatchers("/users/**", "/reports/**").hasRole("ADMIN")

                        // 4. Regra Final: Qualquer outra requisição deve estar autenticada
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(
                "http://localhost:3000", // Para continuar funcionando no seu computador
                "https://vet-clinic-api-front.vercel.app" // SUA URL DE PRODUÇÃO
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
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