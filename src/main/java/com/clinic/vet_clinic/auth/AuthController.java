package com.clinic.vet_clinic.auth;

import com.clinic.vet_clinic.auth.AuthRequestDTO;
import com.clinic.vet_clinic.auth.AuthResponseDTO;
import com.clinic.vet_clinic.user.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO request) {
        // O AuthenticationManager usa o CustomUserDetailsService para validar
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Se a autenticação for bem-sucedida, gera o token
        var user = (UserModel) authentication.getPrincipal();
        var jwtToken = tokenService.generateToken(user);
        return ResponseEntity.ok(new AuthResponseDTO(jwtToken));
    }
}
