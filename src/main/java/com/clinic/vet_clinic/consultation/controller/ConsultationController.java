// ConsultationController.java
package com.clinic.vet_clinic.consultation.controller;

import com.clinic.vet_clinic.consultation.dto.ConsultationRequestDTO;
import com.clinic.vet_clinic.consultation.dto.ConsultationResponseDTO;
import com.clinic.vet_clinic.consultation.service.ConsultationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
}
