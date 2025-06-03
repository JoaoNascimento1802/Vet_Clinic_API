package com.clinic.vet_clinic.pet.controller; // Ajuste o pacote conforme sua estrutura

import com.clinic.vet_clinic.pet.model.PetModel;
import com.clinic.vet_clinic.pet.repository.PetRepository;
import com.clinic.vet_clinic.user.repository.UserRepository; // Para buscar o usuário dono do pet
import com.clinic.vet_clinic.user.model.UserModel; // Para associar o pet ao usuário
import com.clinic.vet_clinic.config.CloudinaryService; // Para o serviço de upload de imagem
import com.clinic.vet_clinic.pet.dto.PetRequestDTO; // Seu DTO de requisição
import com.clinic.vet_clinic.pet.dto.PetResponseDTO; // Seu DTO de resposta (opcional, mas boa prática)

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid; // Para validação do DTO
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors; // Para mapear para DTO de resposta

@RestController
@RequestMapping("/pets")
@Tag(name = "Pets", description = "Endpoints relacionados aos pets dos usuários")
public class PetController {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private UserRepository userRepository; // Injetado para buscar o UserModel

    @Autowired
    private CloudinaryService cloudinaryService; // Injetado para upload de imagem

    @GetMapping
    @Operation(summary = "Listar todos os pets")
    public ResponseEntity<List<PetResponseDTO>> getAllPets() {
        List<PetModel> pets = petRepository.findAll();
        List<PetResponseDTO> petDTOs = pets.stream()
                .map(this::convertToPetResponseDTO)
                .collect(Collectors.toList());

        return petDTOs.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(petDTOs)
                : ResponseEntity.ok(petDTOs);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pet por ID")
    public ResponseEntity<PetResponseDTO> getPetById(@PathVariable Long id) {
        Optional<PetModel> petOptional = petRepository.findById(id);
        return petOptional.map(this::convertToPetResponseDTO) // Converte para DTO de resposta
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    @Operation(summary = "Cadastrar um novo pet com imagem")
    public ResponseEntity<?> createPet(
            @RequestPart("pet") @Valid PetRequestDTO petDto, // Recebe o PetRequestDTO validado
            @RequestPart(value = "image", required = false) MultipartFile image) { // Imagem opcional

        Optional<UserModel> owner = userRepository.findById(petDto.usuarioId());
        if (owner.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário dono do pet não encontrado.");
        }

        PetModel pet = new PetModel();
        pet.setName(petDto.name());
        pet.setAge(petDto.age());
        pet.setSpeciespet(petDto.speciespet());
        pet.setPersonalizatedSpecies(petDto.personalizatedSpecies());
        pet.setGender(petDto.gender());
        pet.setPersonalizedBreed(petDto.personalizedBreed());
        pet.setDogBreed(petDto.dogBreed());
        pet.setCatBreed(petDto.catBreed());
        pet.setBirdBreed(petDto.birdBreed());
        pet.setFishBreed(petDto.fishBreed());
        pet.setRabbitBreed(petDto.rabbitBreed());
        pet.setReptileBreed(petDto.reptileBreed());
        pet.setRodentBreed(petDto.rodentBreed());

        pet.setUsuario(owner.get());

        try {
            if (image != null && !image.isEmpty()) {
                Map<String, Object> uploadResult = cloudinaryService.uploadImage(image);
                String imageUrl = uploadResult.get("secure_url").toString();
                pet.setImageurl(imageUrl); // Salva a URL da imagem no PetModel
            } else if (petDto.imageurl() != null && !petDto.imageurl().isEmpty()) {
                pet.setImageurl(petDto.imageurl());
            }

            PetModel savedPet = petRepository.save(pet);
            return ResponseEntity.status(HttpStatus.CREATED).body(convertToPetResponseDTO(savedPet)); // Retorna o DTO de resposta

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao fazer upload da imagem: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao cadastrar pet: " + e.getMessage());
        }
    }


    @PutMapping("/{id}")
    @Operation(summary = "Atualizar pet pelo ID (com opção de nova imagem)")
    public ResponseEntity<?> updatePet(
            @PathVariable Long id,
            @RequestPart("pet") @Valid PetRequestDTO petDto, // Recebe o PetRequestDTO validado
            @RequestPart(value = "image", required = false) MultipartFile image) { // Nova imagem opcional

        Optional<PetModel> existingPetOptional = petRepository.findById(id);

        if (existingPetOptional.isPresent()) {
            PetModel existingPet = existingPetOptional.get();

            Optional<UserModel> newOwner = userRepository.findById(petDto.usuarioId());
            if (newOwner.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Novo usuário dono do pet não encontrado.");
            }
            existingPet.setUsuario(newOwner.get()); // Associa o novo UserModel

            existingPet.setName(petDto.name());
            existingPet.setAge(petDto.age());
            existingPet.setSpeciespet(petDto.speciespet());
            existingPet.setPersonalizatedSpecies(petDto.personalizatedSpecies());
            existingPet.setGender(petDto.gender());
            existingPet.setPersonalizedBreed(petDto.personalizedBreed());
            existingPet.setDogBreed(petDto.dogBreed());
            existingPet.setCatBreed(petDto.catBreed());
            existingPet.setBirdBreed(petDto.birdBreed());
            existingPet.setFishBreed(petDto.fishBreed());
            existingPet.setRabbitBreed(petDto.rabbitBreed());
            existingPet.setReptileBreed(petDto.reptileBreed());
            existingPet.setRodentBreed(petDto.rodentBreed());

            try {
                if (image != null && !image.isEmpty()) {
                    Map<String, Object> uploadResult = cloudinaryService.uploadImage(image);
                    String imageUrl = uploadResult.get("secure_url").toString();
                    existingPet.setImageurl(imageUrl); // Atualiza com nova URL
                } else if (petDto.imageurl() == null || petDto.imageurl().isEmpty()) {
                    existingPet.setImageurl(null);
                } else {
                    existingPet.setImageurl(petDto.imageurl());
                }

                PetModel savedPet = petRepository.save(existingPet);
                return ResponseEntity.ok(convertToPetResponseDTO(savedPet)); // Retorna o DTO de resposta

            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Erro ao fazer upload da imagem: " + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Erro ao atualizar pet: " + e.getMessage());
            }

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pet não encontrado.");
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar pet pelo ID")
    public ResponseEntity<String> deletePet(@PathVariable Long id) {
        if (petRepository.existsById(id)) {
            petRepository.deleteById(id);
            return ResponseEntity.ok("Pet deletado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pet não encontrado.");
        }
    }

    private PetResponseDTO convertToPetResponseDTO(PetModel pet) {
        return new PetResponseDTO(
                pet.getId(),
                pet.getName(),
                pet.getAge(),
                pet.getImageurl(),
                pet.getPersonalizatedSpecies(),
                pet.getPersonalizedBreed(),
                pet.getSpeciespet(),
                pet.getGender(),
                pet.getBirdBreed(),
                pet.getCatBreed(),
                pet.getDogBreed(),
                pet.getFishBreed(),
                pet.getRabbitBreed(),
                pet.getReptileBreed(),
                pet.getRodentBreed()
        );
    }
}