package com.clinic.vet_clinic.consultation.mapper;

import com.clinic.vet_clinic.consultation.dto.ConsultationResponseDTO;
import com.clinic.vet_clinic.consultation.model.ConsultationModel;
import org.springframework.stereotype.Component;

@Component
public class ConsultationMapper {

    public ConsultationResponseDTO toDTO(ConsultationModel model) {
        // Este método já está correto, convertendo a entidade para o DTO de resposta.
        return new ConsultationResponseDTO(
                model.getId(),
                model.getConsultationdate(),
                model.getConsultationtime(),
                model.getSpecialityEnum().getDescricao(),
                model.getStatus(),
                model.getReason(),
                model.getObservations(),
                model.getPet() != null ? model.getPet().getName() : "N/A",
                model.getVeterinario() != null ? model.getVeterinario().getName() : "N/A",
                model.getClinica() != null ? model.getClinica().getName() : "N/A",
                model.getUsuario() != null ? model.getUsuario().getId() : null
        );
    }
}