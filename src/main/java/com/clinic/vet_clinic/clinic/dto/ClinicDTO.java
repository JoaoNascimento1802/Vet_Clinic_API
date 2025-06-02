package com.clinic.vet_clinic.clinic.dto;

import com.clinic.vet_clinic.clinic.careservices.CareServices;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "Sistema de Clinicas")
public record ClinicDTO(
        @Schema(description = "ID do usuário", example = "123e4567-e89b-12d3-a456-426614174000")
        Long id,

        @Schema(description = "Nome de usuário", example = "clinica do joaozinho")
        String name,

        @Schema(description = "Email do usuário", example = "joao@email.com")
        String email,

        @Schema(description = "Telefone", example = "11999999999")
        String phone,

        @Schema(description = "Endereço completo", example = "Rua Exemplo, 123 - São Paulo")
        String address,

       @Schema(description = "Endereço completo", example = "Rua Exemplo, 123 - São Paulo")
        CareServices careServices,

        @Schema(description = "Imagem da clinica ", example = "https://i.pinimg.com/736x/1e/f6/42/1ef642c4c5864a930b260941dff37711.jpg")
        String imageurl
) {


}
