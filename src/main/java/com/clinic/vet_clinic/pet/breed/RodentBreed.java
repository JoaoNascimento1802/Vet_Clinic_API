package com.clinic.vet_clinic.pet.breed;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(description = "Raças de Roedores")
public enum RodentBreed {
    @Schema(description = "Hamster Sírio")
    HAMSTER_SIRIO("Hamster Sírio"),

    @Schema(description = "Hamster Anão Russo")
    HAMSTER_ANAO_RUSSO("Hamster Anão Russo"),

    @Schema(description = "Rato Twister")
    RATO_TWISTER("Rato Twister"),

    @Schema(description = "Porquinho da Índia Inglês")
    PORQUINHO_DA_INDIA_INGLES("Porquinho da Índia Inglês"),

    @Schema(description = "Porquinho da Índia Peruano")
    PORQUINHO_DA_INDIA_PERUANO("Porquinho da Índia Peruano"),

    @Schema(description = "Chinchila")
    CHINCHILA("Chinchila"),

    @Schema(description = "Gerbil")
    GERBIL("Gerbil"),

    @Schema(description = "Esquilo da Mongólia")
    ESQUILO_DA_MONGOLIA("Esquilo da Mongólia"),

    @Schema(description = "Outro")
    OUTRO("Outro");

    private final String rodentrace;
}
