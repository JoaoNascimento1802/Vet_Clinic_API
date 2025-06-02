package com.clinic.vet_clinic.pet.breed;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(description = "Raças de Répteis")
public enum ReptileBreed {
    @Schema(description = "Dragão Barbudo")
    DRAGAO_BARBUDO("Dragão Barbudo"),

    @Schema(description = "Corn Snake")
    CORN_SNAKE("Corn Snake"),

    @Schema(description = "Tartaruga Tigre D'água")
    TARTARUGA_TIGRE_DAGUA("Tartaruga Tigre D'água"),

    @Schema(description = "Leopardo Gecko")
    LEOPARDO_GECKO("Leopardo Gecko"),

    @Schema(description = "Iguana Verde")
    IGUANA_VERDE("Iguana Verde"),

    @Schema(description = "Píton Real")
    PITON_REAL("Píton Real"),

    @Schema(description = "Jiboia")
    JIBOIA("Jiboia"),

    @Schema(description = "Camaleão")
    CAMALEAO("Camaleão"),

    @Schema(description = "Outro")
    OUTRO("Outro");

    private final String reptilerace;
}