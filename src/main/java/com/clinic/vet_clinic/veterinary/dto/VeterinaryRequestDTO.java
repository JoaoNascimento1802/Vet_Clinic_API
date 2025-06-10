package com.clinic.vet_clinic.veterinary.dto;

import com.clinic.vet_clinic.veterinary.speciality.SpecialityEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record VeterinaryRequestDTO(
        @NotBlank @Size(min = 3, max = 50)
        String name,

        // A senha é opcional na atualização, então não a marcamos como @NotBlank aqui
        String password,

        @Email @NotBlank @Size(max = 100)
        String email,

        @NotBlank @Size(min = 6, max = 15)
        @Pattern(regexp = "^[A-Za-z]{2}\\s?\\d+$", message = "Formato de CRMV inválido")
        String crmv,

        SpecialityEnum specialityenum,

        @NotBlank @Pattern(regexp = "^\\d{2}\\d{8,9}$")
        String phone,

        @NotBlank
        String imageurl
) {}
