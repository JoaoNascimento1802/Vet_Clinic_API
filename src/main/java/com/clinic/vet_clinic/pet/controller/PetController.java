package com.clinic.vet_clinic.pet.controller; // Ajuste o pacote conforme sua estrutura

import com.clinic.vet_clinic.pet.model.PetModel;
import com.clinic.vet_clinic.pet.repository.PetRepository;
import com.clinic.vet_clinic.user.repository.UserRepository;
import com.clinic.vet_clinic.user.model.UserModel;
// Removendo import da CloudinaryService, pois não será usada para upload direto
// import com.clinic.vet_clinic.config.CloudinaryService;
import com.clinic.vet_clinic.pet.dto.PetRequestDTO;
import com.clinic.vet_clinic.pet.dto.PetResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
// Removendo import de MultipartFile, pois não será usado para upload direto
// import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pets")
@Tag(name = "Pets", description = "Endpoints relacionados aos pets dos usuários")
public class PetController {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private UserRepository userRepository;

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
        return petOptional.map(this::convertToPetResponseDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    @PostMapping
    @Operation(summary = "Cadastrar um novo pet recebendo a URL da imagem")
    public ResponseEntity<?> createPet(
            @RequestBody @Valid PetRequestDTO petDto) { // Agora recebe @RequestBody (JSON puro)

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

        // Agora, a URL da imagem vem diretamente do DTO
        if (petDto.imageurl() != null && !petDto.imageurl().isEmpty()) {
            pet.setImageurl(petDto.imageurl());
        } else {
            // Opcional: Você pode definir uma URL padrão ou deixar nulo se não for fornecida.
            // pet.setImageurl("https://example.com/default_pet_image.jpg");
            pet.setImageurl(null); // Deixar nulo se nenhuma URL for fornecida
        }

        try {
            PetModel savedPet = petRepository.save(pet);
            return ResponseEntity.status(HttpStatus.CREATED).body(convertToPetResponseDTO(savedPet));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao cadastrar pet: " + e.getMessage());
        }
    }


    @PutMapping("/{id}")
    @Operation(summary = "Atualizar pet pelo ID recebendo a URL da imagem")
    public ResponseEntity<?> updatePet(
            @PathVariable Long id,
            @RequestBody @Valid PetRequestDTO petDto) { // Agora recebe @RequestBody (JSON puro)

        Optional<PetModel> existingPetOptional = petRepository.findById(id);

        if (existingPetOptional.isPresent()) {
            PetModel existingPet = existingPetOptional.get();

            Optional<UserModel> newOwner = userRepository.findById(petDto.usuarioId());
            if (newOwner.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Novo usuário dono do pet não encontrado.");
            }
            existingPet.setUsuario(newOwner.get());

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

            // Agora, a URL da imagem vem diretamente do DTO
            if (petDto.imageurl() == null || petDto.imageurl().isEmpty()) {
                existingPet.setImageurl(null); // Remove a URL se for vazia/nula no DTO
            } else {
                existingPet.setImageurl(petDto.imageurl()); // Atualiza com a URL do DTO
            }

            try {
                PetModel savedPet = petRepository.save(existingPet);
                return ResponseEntity.ok(convertToPetResponseDTO(savedPet));

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