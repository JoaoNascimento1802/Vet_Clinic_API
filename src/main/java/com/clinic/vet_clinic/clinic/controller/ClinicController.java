package com.clinic.vet_clinic.clinic.controller;

import com.clinic.vet_clinic.clinic.dto.ClinicRequestDTO; // Importa o Request DTO
import com.clinic.vet_clinic.clinic.dto.ClinicResponseDTO;
import com.clinic.vet_clinic.clinic.mapper.ClinicMapper;
import com.clinic.vet_clinic.clinic.model.ClinicModel;
import com.clinic.vet_clinic.clinic.repository.ClinicRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clinic")
@Tag(name = "Clinicas", description = "Endpoints relacionados ao cadastro de clinicas")
@RequiredArgsConstructor
public class ClinicController {

    private final ClinicRepository clinicRepository;
    private final ClinicMapper clinicMapper;

    @GetMapping
    public ResponseEntity<List<ClinicResponseDTO>> getAllClinic() {
        List<ClinicModel> clinics = clinicRepository.findAll();
        List<ClinicResponseDTO> clinicDTOs = clinics.stream()
                .map(clinicMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(clinicDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClinicResponseDTO> getClinicById(@PathVariable Long id) {
        return clinicRepository.findById(id)
                .map(clinicMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Adicionar Clinicas")
    public ResponseEntity<ClinicResponseDTO> addClinic(@Valid @RequestBody ClinicRequestDTO clinicRequest) {
        // 1. Converte o DTO recebido para a entidade do banco
        ClinicModel newClinic = clinicMapper.toModel(clinicRequest);
        // 2. Salva a entidade
        ClinicModel savedClinic = clinicRepository.save(newClinic);
        // 3. Converte a entidade salva para um DTO de resposta e retorna
        return ResponseEntity.status(HttpStatus.CREATED).body(clinicMapper.toDTO(savedClinic));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Alterar clinica pelo ID")
    public ResponseEntity<ClinicResponseDTO> updateClinic(@PathVariable Long id, @Valid @RequestBody ClinicRequestDTO clinicRequest) {
        return clinicRepository.findById(id)
                .map(existingClinic -> {
                    // Atualiza os campos da entidade existente com os dados do DTO
                    existingClinic.setName(clinicRequest.name());
                    existingClinic.setEmail(clinicRequest.email());
                    existingClinic.setPhone(clinicRequest.phone());
                    existingClinic.setAddress(clinicRequest.address());
                    existingClinic.setCareServices(clinicRequest.careServices());
                    existingClinic.setImageurl(clinicRequest.imageurl());

                    ClinicModel updatedClinic = clinicRepository.save(existingClinic);
                    // Converte a entidade atualizada para um DTO de resposta
                    return ResponseEntity.ok(clinicMapper.toDTO(updatedClinic));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar clinica pelo ID")
    public ResponseEntity<Object> deleteClinic(@PathVariable Long id) {
        return clinicRepository.findById(id)
                .map(existing -> {
                    clinicRepository.delete(existing);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}