package com.clinic.vet_clinic.consultation.mapper;

import com.clinic.vet_clinic.consultation.dto.ConsultationResponseDTO;
import com.clinic.vet_clinic.consultation.model.ConsultationModel;
import org.springframework.stereotype.Component;

@Component
public class ConsultationMapper {

    public ConsultationResponseDTO toDTO(ConsultationModel model) {
        return new ConsultationResponseDTO(
                model.getId(),
                model.getConsultationdate(),
                model.getConsultationtime(),
                model.getSpecialityEnum().getDescricao(),
                model.getStatus(),
                model.getReason(),
                model.getObservations(),
                model.getPet() != null ? model.getPet().getName() : "N/A", // Pega o nome do pet
                model.getVeterinario() != null ? model.getVeterinario().getName() : "N/A", // Pega o nome do vet
                model.getClinica() != null ? model.getClinica().getName() : "N/A", // Pega o nome da clínica
                model.getUsuario() != null ? model.getUsuario().getId() : null
        );
    }
}