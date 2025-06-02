package com.clinic.vet_clinic.pet.species;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(description = "Especie dos Pets")
public enum Species {
    @Schema(description = "Cachorro")
    CACHORRO("Cachorro"),

    @Schema(description = "Gato")
    GATO("Gato"),

    @Schema(description = "Pássaro")
    PASSARO("Pássaro"),

    @Schema(description = "Peixe")
    PEIXE("Peixe"),

    @Schema(description = "Roedor")
    ROEDOR("Roedor"),

    @Schema(description = "Réptil")
    REPTIL("Réptil"),

    @Schema(description = "Coelho")
    COELHO("Coelho"),

    @Schema(description = "Outros")
    OUTROS("Outros");

    private final String speciespet;

}
