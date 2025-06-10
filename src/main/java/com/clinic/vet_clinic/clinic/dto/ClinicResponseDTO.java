package com.clinic.vet_clinic.clinic.dto;

import com.clinic.vet_clinic.clinic.careservices.CareServices;

public record ClinicResponseDTO(
        Long id,
        String name,
        String email,
        String phone,
        String address,
        CareServices careServices,
        String imageurl
) {
}
