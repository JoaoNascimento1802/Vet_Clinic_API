package com.clinic.vet_clinic.veterinary.controller;

import com.clinic.vet_clinic.veterinary.dto.VeterinaryRequestDTO;
import com.clinic.vet_clinic.veterinary.dto.VeterinaryResponseDTO;
import com.clinic.vet_clinic.veterinary.mapper.VeterinaryMapper;
import com.clinic.vet_clinic.veterinary.model.VeterinaryModel;
import com.clinic.vet_clinic.veterinary.repository.VeterinaryRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor; // Garanta que este import existe
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder; // Garanta que este import existe
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/veterinary")
@Tag(name = "Veterinários", description = "Endpoints relacionados aos veterinários da clínica")
@RequiredArgsConstructor // <-- 1. ADICIONE ESTA ANOTAÇÃO NA CLASSE
public class VeterinaryController {

    private final VeterinaryRepository veterinaryRepository;
    private final VeterinaryMapper veterinaryMapper;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    @Operation(summary = "Listar todos os veterinários")
    public ResponseEntity<List<VeterinaryResponseDTO>> getAllVeterinaries() {
        List<VeterinaryModel> veterinaries = veterinaryRepository.findAll();
        List<VeterinaryResponseDTO> dtoList = veterinaries.stream()
                .map(veterinaryMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar veterinário por ID")
    public ResponseEntity<VeterinaryResponseDTO> getVeterinaryById(@PathVariable Long id) {
        return veterinaryRepository.findById(id)
                .map(veterinaryMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Cadastrar um novo veterinário")
    public ResponseEntity<VeterinaryResponseDTO> createVeterinary(@Valid @RequestBody VeterinaryRequestDTO requestDTO) {
        // Converte o DTO de requisição para a entidade do banco
        VeterinaryModel newVeterinary = veterinaryMapper.toModel(requestDTO);
        // Codifica a senha antes de salvar
        newVeterinary.setPassword(passwordEncoder.encode(requestDTO.password()));

        VeterinaryModel savedVeterinary = veterinaryRepository.save(newVeterinary);
        // Retorna o DTO de resposta, que é seguro
        return ResponseEntity.status(HttpStatus.CREATED).body(veterinaryMapper.toDTO(savedVeterinary));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar veterinário pelo ID")
    public ResponseEntity<VeterinaryResponseDTO> updateVeterinary(@PathVariable Long id, @Valid @RequestBody VeterinaryRequestDTO requestDTO) {
        return veterinaryRepository.findById(id)
                .map(existingVeterinary -> {
                    // Atualiza os campos da entidade existente com os dados do DTO
                    existingVeterinary.setName(requestDTO.name());
                    existingVeterinary.setEmail(requestDTO.email());
                    existingVeterinary.setCrmv(requestDTO.crmv());
                    existingVeterinary.setSpecialityenum(requestDTO.specialityenum());
                    existingVeterinary.setPhone(requestDTO.phone());
                    existingVeterinary.setImageurl(requestDTO.imageurl());

                    // Atualiza a senha apenas se uma nova for fornecida
                    if (requestDTO.password() != null && !requestDTO.password().isEmpty()) {
                        existingVeterinary.setPassword(passwordEncoder.encode(requestDTO.password()));
                    }

                    VeterinaryModel updatedVeterinary = veterinaryRepository.save(existingVeterinary);
                    // Retorna o DTO de resposta
                    return ResponseEntity.ok(veterinaryMapper.toDTO(updatedVeterinary));
                })
                .orElse(ResponseEntity.notFound().build());
    }

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