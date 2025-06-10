package com.clinic.vet_clinic.pet.breed;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(description = "Raças de Pássaros")
public enum BirdBreed {

    @Schema(description = "Calopsita")
    CALOPSITA("Calopsita"),

    @Schema(description = "Canário")
    CANARIO("Canário"),

    @Schema(description = "Periquito Australiano")
    PERIQUITO_AUSTRALIANO("Periquito Australiano"),

    @Schema(description = "Agapornis")
    AGAPORNIS("Agapornis"),

    @Schema(description = "Ringneck")
    RINGNECK("Ringneck"),

    @Schema(description = "Cacatua")
    CACATUA("Cacatua"),

    @Schema(description = "Arara")
    ARARA("Arara"),

    @Schema(description = "Papagaio Verdadeiro")
    PAPAGAIO_VERDADEIRO("Papagaio Verdadeiro"),

    @Schema(description = "Outro")
    OUTRO("Outro");

    private final String birdrace;

}