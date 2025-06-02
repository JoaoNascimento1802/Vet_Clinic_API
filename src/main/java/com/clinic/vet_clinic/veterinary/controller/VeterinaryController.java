package com.clinic.vet_clinic.veterinary.controller;

import com.clinic.vet_clinic.veterinary.model.VeterinaryModel;
import com.clinic.vet_clinic.veterinary.repository.VeterinaryRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/veterinary")
@Tag(name = "Veterinary", description = "Gerenciamento de Veterinários")
@RequiredArgsConstructor
public class VeterinaryController {

    @Autowired
    VeterinaryRepository veterinaryRepository;

    @GetMapping
    @Operation(summary = "Listar todos os veterinários")
    public ResponseEntity<List<VeterinaryModel>> getAllVeterinary() {
        List<VeterinaryModel> veterinary = veterinaryRepository.findAll();
        return ResponseEntity.ok().body(veterinary);
    }

    @PostMapping
    @Operation(summary = "Cadastrar um novo veterinário")
    public ResponseEntity<VeterinaryModel> addVeterinary(@RequestBody VeterinaryModel veterinary) {
        VeterinaryModel savedVeterinary = veterinaryRepository.save(veterinary);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVeterinary);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar veterinário por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Veterinário atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Veterinário não encontrado")
    })
    public ResponseEntity<VeterinaryModel> updateVeterinary(@PathVariable Long id, @RequestBody VeterinaryModel veterinary) {
        return veterinaryRepository.findById(id)
                .map(existing -> {
                    existing.setName(veterinary.getName());
                    existing.setEmail(veterinary.getEmail());
                    existing.setPhone(veterinary.getPhone());
                    existing.setCrmv(veterinary.getCrmv());
                    existing.setSpecialityenum(veterinary.getSpecialityenum());
                    existing.setImageurl(veterinary.getImageurl());
                    existing.setPassword(veterinary.getPassword());
                    return ResponseEntity.ok(veterinaryRepository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar veterinário por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Veterinário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Veterinário não encontrado")
    })
    public ResponseEntity<Object> deleteVeterinary(@PathVariable Long id, @RequestBody VeterinaryModel veterinary) {
        return veterinaryRepository.findById(id)
                .map(veterinaryModel -> {
                    veterinaryRepository.delete(veterinaryModel);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
