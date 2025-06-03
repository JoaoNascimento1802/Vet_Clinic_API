package com.clinic.vet_clinic.user.controller; // Ajuste o pacote conforme sua estrutura

import com.clinic.vet_clinic.user.model.UserModel;
import com.clinic.vet_clinic.user.repository.UserRepository;
import com.clinic.vet_clinic.config.CloudinaryService; // Importar o serviço Cloudinary

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder; // Para criptografar a senha
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile; // Para o arquivo de imagem

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
// @CrossOrigin(origins = "*") // Use o CorsConfig global, ou especifique aqui se precisar de regras diferentes
@Tag(name = "Usuários", description = "Endpoints relacionados aos usuários da clínica")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CloudinaryService cloudinaryService; // Injete o CloudinaryService

    @Autowired
    private PasswordEncoder passwordEncoder; // Injete o PasswordEncoder

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

    // POST /users - Criar novo usuário com imagem
    @PostMapping
    @Operation(summary = "Cadastrar um novo usuário com imagem")
    public ResponseEntity<?> createUser(
            @RequestPart("user") UserModel user, // Dados do usuário
            @RequestPart(value = "image", required = false) MultipartFile image) { // Imagem opcional

        try {
            // Se uma imagem foi fornecida, faça o upload para a Cloudinary
            if (image != null && !image.isEmpty()) {
                Map<String, Object> uploadResult = cloudinaryService.uploadImage(image);
                String imageUrl = uploadResult.get("secure_url").toString();
                user.setImageurl(imageUrl); // Salve a URL da imagem no modelo do usuário
            }

            // Criptografa a senha antes de salvar
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            UserModel savedUser = userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao fazer upload da imagem: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }

    // PUT /users/{id} - Atualizar usuário (com ou sem nova imagem)
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário pelo ID (com opção de nova imagem)")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @RequestPart("user") UserModel updatedUser, // Dados do usuário atualizados
            @RequestPart(value = "image", required = false) MultipartFile image) { // Nova imagem opcional

        Optional<UserModel> existingUserOptional = userRepository.findById(id);

        if (existingUserOptional.isPresent()) {
            UserModel existingUser = existingUserOptional.get();

            // Atualiza os campos que podem ser alterados
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPhone(updatedUser.getPhone());
            existingUser.setAddress(updatedUser.getAddress());
            existingUser.setRg(updatedUser.getRg());
            // Role e Password geralmente têm lógica de atualização separada ou mais restrita
            // Se a senha for alterada, criptografe-a:
            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            }
            //existingUser.setRole(updatedUser.getRole()); // Cuidado ao permitir a atualização de role diretamente

            try {
                // Se uma nova imagem foi fornecida, faça o upload e atualize a URL
                if (image != null && !image.isEmpty()) {
                    Map<String, Object> uploadResult = cloudinaryService.uploadImage(image);
                    String imageUrl = uploadResult.get("secure_url").toString();
                    existingUser.setImageurl(imageUrl);
                } else if (updatedUser.getImageurl() == null || updatedUser.getImageurl().isEmpty()) {
                    // Se nenhuma nova imagem foi fornecida E a URL no JSON veio vazia/nula, pode ser para remover a imagem
                    existingUser.setImageurl(null); // Ou você pode ter uma lógica para deletar da Cloudinary
                }
                // Se image é nula e updatedUser.getImageurl() não é nula/vazia, mantém a imagem existente

                UserModel savedUser = userRepository.save(existingUser);
                return ResponseEntity.ok(savedUser);

            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Erro ao fazer upload da imagem: " + e.getMessage());
            } catch (Exception e) {
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