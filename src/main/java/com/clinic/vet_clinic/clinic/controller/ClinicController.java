package com.clinic.vet_clinic.clinic.controller;

import com.clinic.vet_clinic.clinic.model.ClinicModel;
import com.clinic.vet_clinic.clinic.repository.ClinicRepository;
import com.clinic.vet_clinic.user.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/clinic")
@Tag(name = "Clinicas", description = "Endpoints relacionados ao cadastro de clinicas")
@RequiredArgsConstructor
public class ClinicController {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private ClinicRepository clinicRepository;

    @GetMapping
    @Operation(summary = "Listar todos os veterinários")
    public ResponseEntity<List<ClinicModel>> getAllClinic() {
        List<ClinicModel> clinic = clinicRepository.findAll();
        return ResponseEntity.ok().body(clinic);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar clinica pelo ID")
    public ResponseEntity<ClinicModel> getClinicById(@PathVariable Long id) {
        return clinicRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Adicionar Clinicas")
    public ResponseEntity<ClinicModel> addClinic(@RequestBody ClinicModel clinic) {
        ClinicModel savedClinic = clinicRepository.save(clinic);
        return ResponseEntity.ok(savedClinic);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Alterar clinica pelo ID")
    public ResponseEntity<ClinicModel> updateClinic(@PathVariable Long id, @RequestBody ClinicModel clinic) {
        return clinicRepository.findById(id)
                .map(existing ->{
                    existing.setName(clinic.getName());
                    existing.setEmail(clinic.getEmail());
                    existing.setPhone(clinic.getPhone());
                    existing.setAddress(clinic.getAddress());
                    existing.setCareServices(clinic.getCareServices());
                    existing.setImageurl(clinic.getImageurl());
                    return ResponseEntity.ok(clinicRepository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Deletar clinica pelo ID")
    public ResponseEntity<Object> deleteClinic(@PathVariable Long id) {
        return clinicRepository.findById(id)
                .map(existing ->{
                    clinicRepository.delete(existing);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
