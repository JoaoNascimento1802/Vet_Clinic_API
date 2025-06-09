package com.clinic.vet_clinic.pet.mapper;

import com.clinic.vet_clinic.pet.dto.PetRequestDTO;
import com.clinic.vet_clinic.pet.dto.PetResponseDTO;
import com.clinic.vet_clinic.pet.model.PetModel;
import org.springframework.stereotype.Component;

@Component
public class PetMapper {

    public PetModel toModel(PetRequestDTO dto) {
        // Este método não precisa de alteração
        return PetModel.builder()
                .name(dto.name())
                .imageurl(dto.imageurl())
                .age(dto.age())
                .personalizatedSpecies(dto.personalizatedSpecies())
                .personalizedBreed(dto.personalizedBreed())
                .speciespet(dto.speciespet())
                .gender(dto.gender())
                .birdBreed(dto.birdBreed())
                .catBreed(dto.catBreed())
                .dogBreed(dto.dogBreed())
                .fishBreed(dto.fishBreed())
                .rabbitBreed(dto.rabbitBreed())
                .reptileBreed(dto.reptileBreed())
                .rodentBreed(dto.rodentBreed())
                .build();
    }

    public PetResponseDTO toDTO(PetModel model) {
        // Obtém o ID do usuário de forma segura, verificando se não é nulo
        Long ownerId = (model.getUsuario() != null) ? model.getUsuario().getId() : null;

        return new PetResponseDTO(
                model.getId(),
                model.getName(),
                model.getAge(),
                model.getImageurl(),
                model.getPersonalizatedSpecies(),
                model.getPersonalizedBreed(),
                model.getSpeciespet(),
                model.getGender(),
                model.getBirdBreed(),
                model.getCatBreed(),
                model.getDogBreed(),
                model.getFishBreed(),
                model.getRabbitBreed(),
                model.getReptileBreed(),
                model.getRodentBreed(),
                ownerId // <-- AQUI ESTÁ A CORREÇÃO: Adicionando o ID do dono
        );
    }
}