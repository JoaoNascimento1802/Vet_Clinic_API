// ConsultationResponseDTO.java
package com.clinic.vet_clinic.consultation.dto;



import com.clinic.vet_clinic.consultation.status.ConsultationStatus;
import com.clinic.vet_clinic.veterinary.speciality.SpecialityEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record ConsultationResponseDTO(
        Long id,
        LocalDate consultationdate,
        LocalTime consultationtime,
        SpecialityEnum specialityEnum,
        ConsultationStatus status,
        String reason,
        String observations,
        Long petId,
        Long usuarioId,
        Long veterinarioId,
        Long clinicaId,
        LocalDateTime dataCriacao,
        LocalDateTime dataAtualizacao
) {}
