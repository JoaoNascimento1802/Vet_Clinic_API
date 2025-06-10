package com.clinic.vet_clinic.clinic.careservices;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(description = "Serviços oferecidos pelas clínicas")
public enum CareServices {

    @Schema(description = "Clínica Geral")
    CLINICA_GERAL("Clínica Geral"),

    @Schema(description = "Cirurgia")
    CIRURGIA("Cirurgia"),

    @Schema(description = "Odontologia")
    ODONTOLOGIA("Odontologia"),

    @Schema(description = "Oftalmologia")
    OFTALMOLOGIA("Oftalmologia"),

    @Schema(description = "Dermatologia")
    DERMATOLOGIA("Dermatologia"),

    @Schema(description = "Ortopedia")
    ORTOPEDIA("Ortopedia"),

    @Schema(description = "Cardiologia")
    CARDIOLOGIA("Cardiologia"),

    @Schema(description = "Oncologia")
    ONCOLOGIA("Oncologia"),

    @Schema(description = "Anestesiologia")
    ANESTESIOLOGIA("Anestesiologia"),

    @Schema(description = "Exames de Imagem")
    EXAMES_IMAGEM("Exames de Imagem"),

    @Schema(description = "Vacinação")
    VACINACAO("Vacinação"),

    @Schema(description = "Internação")
    INTERNACAO("Internação"),

    @Schema(description = "Emergência")
    EMERGENCIA("Emergência"),

    @Schema(description = "Fisioterapia")
    FISIOTERAPIA("Fisioterapia");

    private final String descricao;
}
