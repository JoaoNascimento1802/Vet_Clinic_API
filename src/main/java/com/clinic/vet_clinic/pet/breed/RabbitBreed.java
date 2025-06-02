package com.clinic.vet_clinic.pet.breed;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(description = "Raças de Coelhos")
public enum RabbitBreed {
    @Schema(description = "Anão Holandês")
    ANAO_HOLANDES("Anão Holandês"),

    @Schema(description = "Mini Lop")
    MINI_LOP("Mini Lop"),

    @Schema(description = "Nova Zelândia Branco")
    NOVA_ZELANDIA_BRANCO("Nova Zelândia Branco"),

    @Schema(description = "Lionhead")
    LIONHEAD("Lionhead"),

    @Schema(description = "Flemish Giant")
    FLEMISH_GIANT("Flemish Giant"),

    @Schema(description = "Holland Lop")
    HOLLAND_LOP("Holland Lop"),

    @Schema(description = "Rex")
    REX("Rex"),

    @Schema(description = "Angorá Inglês")
    ANGORA_INGLES("Angorá Inglês"),

    @Schema(description = "Outro")
    OUTRO("Outro");

    private final String reptilerace;
}
