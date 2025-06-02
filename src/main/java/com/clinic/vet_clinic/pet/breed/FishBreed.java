package com.clinic.vet_clinic.pet.breed;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(description = "Raças de Peixes")
public enum FishBreed {
    @Schema(description = "Beta")
    BETA("Beta"),

    @Schema(description = "Guppy")
    GUPPY("Guppy"),

    @Schema(description = "Goldfish Cometa")
    GOLDFISH_COMETA("Goldfish Cometa"),

    @Schema(description = "Molly")
    MOLLY("Molly"),

    @Schema(description = "Platy")
    PLATY("Platy"),

    @Schema(description = "Tetra Neon")
    TETRA_NEON("Tetra Neon"),

    @Schema(description = "Corydora")
    CORYDORA("Corydora"),

    @Schema(description = "Peixe Palhaço")
    PEIXE_PALHACO("Peixe Palhaço"),

    @Schema(description = "Outro")
    OUTRO("Outro");

    private final String fishrace;
}
