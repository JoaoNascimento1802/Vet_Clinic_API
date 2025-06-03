package com.clinic.vet_clinic.veterinary.controller; // Ajuste o pacote

import com.clinic.vet_clinic.veterinary.model.VeterinaryModel;
import com.clinic.vet_clinic.veterinary.repository.VeterinaryRepository;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@RequestMapping("/veterinary")
@Tag(name = "Veterinários", description = "Endpoints relacionados aos veterinários da clínica")
public class VeterinaryController {

    @Autowired
    private VeterinaryRepository veterinaryRepository;

    // Removendo a injeção da CloudinaryService
    // @Autowired
    // private CloudinaryService cloudinaryService;

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

    @PostMapping
    @Operation(summary = "Cadastrar um novo veterinário recebendo a URL da imagem")
    public ResponseEntity<?> createVeterinary(
            @RequestBody @Valid VeterinaryModel veterinary) { // Agora recebe @RequestBody

        try {
            // A URL da imagem é esperada diretamente no objeto veterinary
            // veterinary.setImageurl(veterinary.getImageurl()); // Isso já é feito pelo Spring ao mapear o JSON

            // Criptografa a senha antes de salvar
            veterinary.setPassword(passwordEncoder.encode(veterinary.getPassword()));
            VeterinaryModel savedVeterinary = veterinaryRepository.save(veterinary);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedVeterinary);

        } catch (Exception e) {
            e.printStackTrace(); // Para depuração
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao cadastrar veterinário: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar veterinário pelo ID recebendo a URL da imagem")
    public ResponseEntity<?> updateVeterinary(
            @PathVariable Long id,
            @RequestBody @Valid VeterinaryModel updatedVeterinary) { // Agora recebe @RequestBody

        Optional<VeterinaryModel> existingVeterinaryOptional = veterinaryRepository.findById(id);

        if (existingVeterinaryOptional.isPresent()) {
            VeterinaryModel existingVeterinary = existingVeterinaryOptional.get();
            // Atualiza os campos que podem ser alterados
            existingVeterinary.setName(updatedVeterinary.getName());
            existingVeterinary.setEmail(updatedVeterinary.getEmail());
            existingVeterinary.setCrmv(updatedVeterinary.getCrmv());
            existingVeterinary.setSpecialityenum(updatedVeterinary.getSpecialityenum());
            existingVeterinary.setPhone(updatedVeterinary.getPhone());
            // A URL da imagem vem diretamente do JSON
            existingVeterinary.setImageurl(updatedVeterinary.getImageurl()); // Atualiza ou define como nulo

            if (updatedVeterinary.getPassword() != null && !updatedVeterinary.getPassword().isEmpty()) {
                existingVeterinary.setPassword(passwordEncoder.encode(updatedVeterinary.getPassword()));
            }

            try {
                VeterinaryModel savedVeterinary = veterinaryRepository.save(existingVeterinary);
                return ResponseEntity.ok(savedVeterinary);

            } catch (Exception e) {
                e.printStackTrace(); // Para depuração
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