package com.clinic.vet_clinic.pet.breed;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(description = "Raças de Cachorros")
public enum DogBreed {
    @Schema(description = "Labrador Retriever")
    LABRADOR_RETRIEVER("Labrador Retriever"),

    @Schema(description = "Golden Retriever")
    GOLDEN_RETRIEVER("Golden Retriever"),

    @Schema(description = "Bulldog Francês")
    BULLDOG_FRANCES("Bulldog Francês"),

    @Schema(description = "Pastor Alemão")
    PASTOR_ALEMAO("Pastor Alemão"),

    @Schema(description = "Poodle")
    POODLE("Poodle"),

    @Schema(description = "Beagle")
    BEAGLE("Beagle"),

    @Schema(description = "Rottweiler")
    ROTTWEILER("Rottweiler"),

    @Schema(description = "Dachshund")
    DACHSHUND("Dachshund"),

    @Schema(description = "Shih Tzu")
    SHIH_TZU("Shih Tzu"),

    @Schema(description = "Outro")
    OUTRO("Outro");

    private final String dograce;
}
