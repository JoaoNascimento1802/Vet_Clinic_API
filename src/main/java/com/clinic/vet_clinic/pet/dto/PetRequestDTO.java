package com.clinic.vet_clinic.pet.dto;

import com.clinic.vet_clinic.pet.breed.*;
import com.clinic.vet_clinic.pet.gender.Gender;
import com.clinic.vet_clinic.pet.species.Species;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@JsonInclude(JsonInclude.Include.NON_NULL)
public record PetRequestDTO(
        @NotBlank
        @Size(min = 3, max = 50)
        String name,

        @Min(0)
        int age,

        @NotNull
        String imageurl,

        @Size(min = 3, max = 50)
        String personalizatedSpecies,

        @Size(min = 3, max = 50)
        String personalizedBreed,

        @NotNull
        Species speciespet,

        @NotNull
        Gender gender,

        BirdBreed birdBreed,
        CatBreed catBreed,
        DogBreed dogBreed,
        FishBreed fishBreed,
        RabbitBreed rabbitBreed,
        ReptileBreed reptileBreed,
        RodentBreed rodentBreed,

        @NotNull
        Long usuarioId
) {}