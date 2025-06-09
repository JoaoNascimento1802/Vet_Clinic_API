package com.clinic.vet_clinic.veterinary.dto;

import com.clinic.vet_clinic.veterinary.speciality.SpecialityEnum;


public record VeterinaryResponseDTO(
        Long id,
        String name,
        String email,
        String crmv,
        SpecialityEnum specialityenum,
        String phone,
        String imageurl
) {}