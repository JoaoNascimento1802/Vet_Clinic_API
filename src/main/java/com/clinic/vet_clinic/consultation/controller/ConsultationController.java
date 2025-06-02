// ConsultationController.java
package com.clinic.vet_clinic.consultation.controller;

import com.clinic.vet_clinic.consultation.dto.ConsultationRequestDTO;
import com.clinic.vet_clinic.consultation.dto.ConsultationResponseDTO;
import com.clinic.vet_clinic.consultation.service.ConsultationService;
import com.clinic.vet_clinic.veterinary.speciality.SpecialityEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/consultas")
@RequiredArgsConstructor
@Tag(name = "Consultas", description = "Endpoints de agendamento e gestão de consultas")
public class ConsultationController {

    private final ConsultationService service;

    @PostMapping
    public ResponseEntity<ConsultationResponseDTO> create(@RequestBody ConsultationRequestDTO dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @GetMapping
    public ResponseEntity<List<ConsultationResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultationResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultationResponseDTO> update(@PathVariable Long id, @RequestBody ConsultationRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-date")
    @Operation(summary = "Buscar consultas por data")
    public ResponseEntity<List<ConsultationResponseDTO>> findByDate(@RequestParam @JsonFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return ResponseEntity.ok(service.findConsultationsByDate(date));
    }

    @GetMapping("/by-speciality")
    @Operation(summary = "Buscar consultas por especialidade do médico")
    public ResponseEntity<List<ConsultationResponseDTO>> findBySpeciality(@RequestParam SpecialityEnum speciality) {
        return ResponseEntity.ok(service.findConsultationsBySpeciality(speciality));
    }

    @GetMapping("/by-veterinary-name")
    @Operation(summary = "Buscar consultas por nome do médico")
    public ResponseEntity<List<ConsultationResponseDTO>> findByVeterinaryName(@RequestParam String name) {
        return ResponseEntity.ok(service.findConsultationsByVeterinaryName(name));
    }

    @GetMapping("/by-pet-name")
    @Operation(summary = "Buscar consultas por nome do paciente (pet)")
    public ResponseEntity<List<ConsultationResponseDTO>> findByPetName(@RequestParam String name) {
        return ResponseEntity.ok(service.findConsultationsByPetName(name));
    }
}
