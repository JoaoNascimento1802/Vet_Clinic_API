package com.clinic.vet_clinic.user.controller; // Ajuste o pacote conforme sua estrutura

import com.clinic.vet_clinic.user.model.UserModel;
import com.clinic.vet_clinic.user.repository.UserRepository;
// Removendo import da CloudinaryService
// import com.clinic.vet_clinic.config.CloudinaryService;

import com.clinic.vet_clinic.user.role.UserRole;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid; // Adicione se estiver usando validação no UserModel
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
// Removendo import de MultipartFile
// import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
// @CrossOrigin(origins = "*") // Use o CorsConfig global, ou especifique aqui se precisar de regras diferentes
@Tag(name = "Usuários", description = "Endpoints relacionados aos usuários da clínica")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Removendo a injeção da CloudinaryService
    // @Autowired
    // private CloudinaryService cloudinaryService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // GET /users - Listar todos os usuários
    @GetMapping
    @Operation(summary = "Listar todos os usuários")
    public ResponseEntity<List<UserModel>> getAllUsers() {
        List<UserModel> users = userRepository.findAll();
        return users.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(users)
                : ResponseEntity.ok(users);
    }

    // GET /users/{id} - Buscar usuário por ID
    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID")
    public ResponseEntity<UserModel> getUserById(@PathVariable Long id) {
        Optional<UserModel> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/register") // <-- Mude aqui para corresponder à regra pública
    @Operation(summary = "Registrar um novo usuário")
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserModel user) { // renomeei de createUser
        try {
            // Define o papel padrão para novos usuários
            user.setRole(UserRole.USER);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            UserModel savedUser = userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
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