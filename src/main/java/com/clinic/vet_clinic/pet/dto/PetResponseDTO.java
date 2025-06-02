package com.clinic.vet_clinic.pet.dto;

import com.clinic.vet_clinic.pet.breed.*;
import com.clinic.vet_clinic.pet.gender.Gender;
import com.clinic.vet_clinic.pet.species.Species;
import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public record PetResponseDTO (
        Long id,
        String name,
        int age,
        String imageurl,
        String personalizatedSpecies,
        String personalizedBreed,
        Species speciespet,
        Gender gender,
        BirdBreed birdBreed,
        CatBreed catBreed,
        DogBreed dogBreed,
        FishBreed fishBreed,
        RabbitBreed rabbitBreed,
        ReptileBreed reptileBreed,
        RodentBreed rodentBreed
){}
