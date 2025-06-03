package com.clinic.vet_clinic.veterinary.controller; // Ajuste o pacote

import com.clinic.vet_clinic.veterinary.model.VeterinaryModel;
import com.clinic.vet_clinic.veterinary.repository.VeterinaryRepository;
import com.clinic.vet_clinic.config.CloudinaryService; // Importar o serviço Cloudinary

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/veterinary")
@Tag(name = "Veterinários", description = "Endpoints relacionados aos veterinários da clínica")
public class VeterinaryController {

    @Autowired
    private VeterinaryRepository veterinaryRepository;

    @Autowired
    private CloudinaryService cloudinaryService; // Injete o CloudinaryService

    @Autowired
    private PasswordEncoder passwordEncoder;

    // GET /veterinary - Listar todos os veterinários
    @GetMapping
    @Operation(summary = "Listar todos os veterinários")
    public ResponseEntity<List<VeterinaryModel>> getAllVeterinaries() {
        List<VeterinaryModel> veterinaries = veterinaryRepository.findAll();
        return veterinaries.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(veterinaries)
                : ResponseEntity.ok(veterinaries);
    }

    // GET /veterinary/{id} - Buscar veterinário por ID
    @GetMapping("/{id}")
    @Operation(summary = "Buscar veterinário por ID")
    public ResponseEntity<VeterinaryModel> getVeterinaryById(@PathVariable Long id) {
        Optional<VeterinaryModel> veterinary = veterinaryRepository.findById(id);
        return veterinary.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // POST /veterinary - Criar novo veterinário com imagem
    @PostMapping
    @Operation(summary = "Cadastrar um novo veterinário com imagem")
    public ResponseEntity<?> createVeterinary(
            @RequestPart("veterinary") VeterinaryModel veterinary,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        try {
            if (image != null && !image.isEmpty()) {
                Map<String, Object> uploadResult = cloudinaryService.uploadImage(image);
                String imageUrl = uploadResult.get("secure_url").toString();
                veterinary.setImageurl(imageUrl);
            }

            veterinary.setPassword(passwordEncoder.encode(veterinary.getPassword()));
            VeterinaryModel savedVeterinary = veterinaryRepository.save(veterinary);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedVeterinary);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao fazer upload da imagem: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao cadastrar veterinário: " + e.getMessage());
        }
    }

    // PUT /veterinary/{id} - Atualizar veterinário (com ou sem nova imagem)
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar veterinário pelo ID (com opção de nova imagem)")
    public ResponseEntity<?> updateVeterinary(
            @PathVariable Long id,
            @RequestPart("veterinary") VeterinaryModel updatedVeterinary,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        Optional<VeterinaryModel> existingVeterinaryOptional = veterinaryRepository.findById(id);

        if (existingVeterinaryOptional.isPresent()) {
            VeterinaryModel existingVeterinary = existingVeterinaryOptional.get();

            existingVeterinary.setName(updatedVeterinary.getName());
            existingVeterinary.setEmail(updatedVeterinary.getEmail());
            existingVeterinary.setCrmv(updatedVeterinary.getCrmv());
            existingVeterinary.setSpecialityenum(updatedVeterinary.getSpecialityenum());
            existingVeterinary.setPhone(updatedVeterinary.getPhone());

            if (updatedVeterinary.getPassword() != null && !updatedVeterinary.getPassword().isEmpty()) {
                existingVeterinary.setPassword(passwordEncoder.encode(updatedVeterinary.getPassword()));
            }

            try {
                if (image != null && !image.isEmpty()) {
                    Map<String, Object> uploadResult = cloudinaryService.uploadImage(image);
                    String imageUrl = uploadResult.get("secure_url").toString();
                    existingVeterinary.setImageurl(imageUrl);
                } else if (updatedVeterinary.getImageurl() == null || updatedVeterinary.getImageurl().isEmpty()) {
                    existingVeterinary.setImageurl(null);
                }

                VeterinaryModel savedVeterinary = veterinaryRepository.save(existingVeterinary);
                return ResponseEntity.ok(savedVeterinary);

            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Erro ao fazer upload da imagem: " + e.getMessage());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Erro ao atualizar veterinário: " + e.getMessage());
            }

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Veterinário não encontrado.");
        }
    }

    // DELETE /veterinary/{id} - Deletar veterinário
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar veterinário pelo ID")
    public ResponseEntity<String> deleteVeterinary(@PathVariable Long id) {
        if (veterinaryRepository.existsById(id)) {
            veterinaryRepository.deleteById(id);
            return ResponseEntity.ok("Veterinário deletado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Veterinário não encontrado.");
        }
    }
}