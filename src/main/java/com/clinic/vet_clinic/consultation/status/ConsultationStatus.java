package com.clinic.vet_clinic.consultation.status;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(description = "Status da Consulta")
public enum ConsultationStatus {

    @Schema(description = "Consulta Agendada")
    AGENDADA("Agendada"),

    @Schema(description = "Consulta Reagendada")
    REAGENDADA("Reagendada"),

    @Schema(description = "Consulta Finalizada")
    FINALIZADA("Finalizada"),

    @Schema(description = "Consulta Cancelada")
    CANCELADA("Cancelada");

    private final String descricao;
}
