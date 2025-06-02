package com.clinic.vet_clinic.pet.gender;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(description = "Gênero dos Pets")
public enum Gender {

    @Schema(description = "Macho")
    Macho("Macho"),

    @Schema(description = "Fêmea")
    Femea("Fêmea");

    private final String genderspet;
}
