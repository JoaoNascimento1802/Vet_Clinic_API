package com.clinic.vet_clinic.pet.controller;

import com.clinic.vet_clinic.pet.dto.PetRequestDTO;
import com.clinic.vet_clinic.pet.dto.PetResponseDTO;
import com.clinic.vet_clinic.pet.mapper.PetMapper;
import com.clinic.vet_clinic.pet.model.PetModel;
import com.clinic.vet_clinic.pet.repository.PetRepository;
import com.clinic.vet_clinic.user.model.UserModel;
import com.clinic.vet_clinic.user.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pets")
@RequiredArgsConstructor
@Tag(name = "Pets", description = "Endpoints relacionados ao gerenciamento de pets")
public class PetController {

    private final PetRepository petRepository;
    private final PetMapper petMapper;
    private final UserRepository userRepository;

    @Operation(summary = "Cadastrar um novo pet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<PetResponseDTO> create(@RequestBody PetRequestDTO dto) {
        PetModel pet = petMapper.toModel(dto);


        UserModel usuario = userRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + dto.usuarioId()));

        pet.setUsuario(usuario); // Associa o UserModel ao PetModel

        PetModel saved = petRepository.save(pet);
        return ResponseEntity.ok(petMapper.toDTO(saved));
    }


    @Operation(summary = "Listar todos os pets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pets retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno ao buscar pets")
    })
    @GetMapping
    public ResponseEntity<List<PetResponseDTO>> getAll() {
        List<PetResponseDTO> pets = petRepository.findAll()
                .stream()
                .map(petMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pets);
    }

    @Operation(summary = "Buscar pet por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet encontrado"),
            @ApiResponse(responseCode = "404", description = "Pet não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PetResponseDTO> getById(@PathVariable Long id) {
        return petRepository.findById(id)
                .map(petMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Atualizar pet por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pet não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PetResponseDTO> update(@PathVariable Long id, @RequestBody PetRequestDTO dto) {
        return petRepository.findById(id).map(existing -> {
            PetModel updated = petMapper.toModel(dto);
            updated.setId(existing.getId());
            PetModel saved = petRepository.save(updated);
            return ResponseEntity.ok(petMapper.toDTO(saved));
        }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deletar pet por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pet deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pet não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return petRepository.findById(id).map(pet -> {
            petRepository.delete(pet);
            return ResponseEntity.noContent().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}