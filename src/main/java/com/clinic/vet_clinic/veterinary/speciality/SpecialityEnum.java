package com.clinic.vet_clinic.veterinary.speciality;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(description = "Especialidades da medicina veterinária")
public enum SpecialityEnum {

    @Schema(description = "Clínico Geral")
    CLINICO_GERAL("Clínico Geral"),

    @Schema(description = "Anestesiologia")
    ANESTESIOLOGIA("Anestesiologia"),

    @Schema(description = "Cardiologia")
    CARDIOLOGIA("Cardiologia"),

    @Schema(description = "Dermatologia")
    DERMATOLOGIA("Dermatologia"),

    @Schema(description = "Endocrinologia")
    ENDOCRINOLOGIA("Endocrinologia"),

    @Schema(description = "Gastroenterologia")
    GASTROENTEROLOGIA("Gastroenterologia"),

    @Schema(description = "Neurologia")
    NEUROLOGIA("Neurologia"),

    @Schema(description = "Nutrição")
    NUTRICAO("Nutrição"),

    @Schema(description = "Oftalmologia")
    OFTALMOLOGIA("Oftalmologia"),

    @Schema(description = "Oncologia")
    ONCOLOGIA("Oncologia"),

    @Schema(description = "Ortopedia")
    ORTOPEDIA("Ortopedia"),

    @Schema(description = "Reprodução Animal")
    REPRODUCAO_ANIMAL("Reprodução Animal"),

    @Schema(description = "Patologia")
    PATOLOGIA("Patologia"),

    @Schema(description = "Cirurgia Geral")
    CIRURGIA_GERAL("Cirurgia Geral"),

    @Schema(description = "Cirurgia Ortopédica")
    CIRURGIA_ORTOPEDICA("Cirurgia Ortopédica"),

    @Schema(description = "Odontologia")
    ODONTOLOGIA("Odontologia"),

    @Schema(description = "Zootecnia")
    ZOOTECNIA("Zootecnia"),

    @Schema(description = "Animais Exóticos")
    EXOTICOS("Animais Exóticos"),

    @Schema(description = "Acupuntura")
    ACUPUNTURA("Acupuntura"),

    @Schema(description = "Fisioterapia")
    FISIOTERAPIA("Fisioterapia"),

    @Schema(description = "Diagnóstico por Imagem")
    IMAGINOLOGIA("Diagnóstico por Imagem");

    private final String descricao;
}
