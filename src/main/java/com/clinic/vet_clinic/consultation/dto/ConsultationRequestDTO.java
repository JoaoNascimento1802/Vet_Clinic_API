package com.clinic.vet_clinic.consultation.dto;

import com.clinic.vet_clinic.consultation.status.ConsultationStatus;
import com.clinic.vet_clinic.veterinary.speciality.SpecialityEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


import java.time.LocalDate;
import java.time.LocalTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ConsultationRequestDTO(

        @NotNull
        @FutureOrPresent
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate consultationdate,

        @NotNull
        @JsonFormat(pattern = "HH:mm")
        LocalTime consultationtime,

        @NotNull
        SpecialityEnum specialityEnum,


        @NotNull
        ConsultationStatus status,

        @NotBlank
        @Size(min = 5, max = 255)
        String reason,

        @NotBlank
        @Size(min = 5, max = 255)
        String observations,

        @NotNull
        Long petId,

        @NotNull
        Long usuarioId,

        @NotNull
        Long veterinarioId,

        @NotNull
        Long clinicaId
) {}
