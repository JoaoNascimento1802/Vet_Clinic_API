package com.clinic.vet_clinic.user.controller; // Ajuste o pacote conforme sua estrutura

import com.clinic.vet_clinic.user.model.UserModel;
import com.clinic.vet_clinic.user.repository.UserRepository;
import com.clinic.vet_clinic.user.dto.UserRequestDTO;
// Removendo import da CloudinaryService
// import com.clinic.vet_clinic.config.CloudinaryService;

import com.clinic.vet_clinic.user.role.UserRole;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid; // Adicione se estiver usando validação no UserModel
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication; // <-- Adicione este import
import org.springframework.security.core.annotation.AuthenticationPrincipal; // <-- Adicione este import
import org.springframework.security.core.userdetails.UserDetails;
// Removendo import de MultipartFile
// import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import com.clinic.vet_clinic.user.dto.UserResponseDTO;
import com.clinic.vet_clinic.user.mapper.UserMapper;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper; // <-- Injete o Mapper

    @GetMapping
    @Operation(summary = "Listar todos os usuários")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserModel> users = userRepository.findAll();
        // Converte a lista de entidades para DTOs
        List<UserResponseDTO> userDTOs = users.stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }

    // GET /users/{id} - Buscar usuário por ID
    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID")
    public ResponseEntity<UserModel> getUserById(@PathVariable Long id) {
        Optional<UserModel> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // ADICIONE ESTE NOVO MÉTODO DENTRO DA CLASSE
    @GetMapping("/me")
    @Operation(summary = "Verificar dados do usuário logado")
    public ResponseEntity<Object> getCurrentUser(Authentication authentication) {
        // Retorna os detalhes do principal (usuário) e suas permissões (authorities)
        // Isso nos dirá exatamente qual é a ROLE que o Spring está vendo.
        return ResponseEntity.ok(authentication.getPrincipal());
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRequestDTO requestDTO) { // <-- MUDANÇA AQUI
        try {
            // Converte o DTO recebido para a entidade
            UserModel user = userMapper.toModel(requestDTO);

            // Define o papel padrão para novos usuários
            user.setRole(UserRole.USER);
            // Codifica a senha
            user.setPassword(passwordEncoder.encode(requestDTO.password()));

            UserModel savedUser = userRepository.save(user);

            // Retorna um DTO de resposta, e não a entidade completa
            return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toDTO(savedUser));
        } catch (Exception e) {
            // Trata erros de constraint (ex: email ou rg duplicado)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao registrar usuário: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário pelo ID recebendo a URL da imagem")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @RequestBody @Valid UserModel updatedUser) { // Agora recebe @RequestBody

        Optional<UserModel> existingUserOptional = userRepository.findById(id);

        if (existingUserOptional.isPresent()) {
            UserModel existingUser = existingUserOptional.get();

            // Atualiza os campos que podem ser alterados
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPhone(updatedUser.getPhone());
            existingUser.setAddress(updatedUser.getAddress());
            existingUser.setRg(updatedUser.getRg());
            // A URL da imagem vem diretamente do JSON
            existingUser.setImageurl(updatedUser.getImageurl()); // Atualiza ou define como nulo

            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            }

            try {
                UserModel savedUser = userRepository.save(existingUser);
                return ResponseEntity.ok(savedUser);

            } catch (Exception e) {
                e.printStackTrace(); // Para depuração
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Erro ao atualizar usuário: " + e.getMessage());
            }

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }
    }

    // DELETE /users/{id} - Deletar usuário
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar usuário pelo ID")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok("Usuário deletado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }
    }
}