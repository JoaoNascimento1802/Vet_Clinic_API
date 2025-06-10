package com.clinic.vet_clinic.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
        @NotBlank @Size(min = 3, max = 50)
        String username,

        @NotBlank
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).+$", message = "A senha deve ser forte.")
        String password,

        @Email @NotBlank @Size(max = 100)
        String email,

        @NotBlank @Pattern(regexp = "^\\d{2}\\d{8,9}$")
        String phone,

        @NotBlank @Size(max = 200)
        String address,

        @NotBlank @Pattern(regexp = "^\\d{7,9}X?$", message = "Formato de RG inválido")
        String rg,

        @NotBlank
        String imageurl
) {}
