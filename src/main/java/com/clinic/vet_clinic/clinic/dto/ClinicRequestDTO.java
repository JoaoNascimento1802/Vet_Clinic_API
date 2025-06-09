package com.clinic.vet_clinic.clinic.dto;

import com.clinic.vet_clinic.clinic.careservices.CareServices;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClinicRequestDTO(
        @NotBlank @Size(min = 3, max = 50)
        String name,

        @Email @NotBlank @Size(max = 100)
        String email,

        @NotBlank @Pattern(regexp = "^\\d{2}\\d{8,9}$")
        String phone,

        @NotBlank @Size(max = 200)
        String address,

        CareServices careServices,

        @NotBlank
        String imageurl
) {}