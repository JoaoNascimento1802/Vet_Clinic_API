package com.clinic.vet_clinic.consultation.dto;

import com.clinic.vet_clinic.consultation.status.ConsultationStatus;
import java.time.LocalDate;
import java.time.LocalTime;

// Este DTO agora terá os nomes em vez dos objetos completos
public record ConsultationResponseDTO(
        Long id,
        LocalDate consultationdate,
        LocalTime consultationtime,
        String speciality,
        ConsultationStatus status,
        String reason,
        String observations,
        String petName,
        String veterinaryName,
        String clinicName,
        Long usuarioId
) {}