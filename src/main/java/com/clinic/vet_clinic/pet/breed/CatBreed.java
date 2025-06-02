package com.clinic.vet_clinic.pet.breed;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(description = "Raças de Gatos")
public enum CatBreed {
    @Schema(description = "Persa")
    PERSA("Persa"),

    @Schema(description = "Siamês")
    SIAMES("Siamês"),

    @Schema(description = "Maine Coon")
    MAINE_COON("Maine Coon"),

    @Schema(description = "Ragdoll")
    RAGDOLL("Ragdoll"),

    @Schema(description = "Bengal")
    BENGAL("Bengal"),

    @Schema(description = "Sphynx")
    SPHYNX("Sphynx"),

    @Schema(description = "British Shorthair")
    BRITISH_SHORTHAIR("British Shorthair"),

    @Schema(description = "Scottish Fold")
    SCOTTISH_FOLD("Scottish Fold"),

    @Schema(description = "Outro")
    OUTRO("Outro");

    private final String catrace;
}
