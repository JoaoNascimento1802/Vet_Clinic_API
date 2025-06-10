package com.clinic.vet_clinic.user.dto;

import com.clinic.vet_clinic.user.role.UserRole;

public record UserResponseDTO(
        Long id,
        String username,
        String email,
        String phone,
        UserRole role
) {}