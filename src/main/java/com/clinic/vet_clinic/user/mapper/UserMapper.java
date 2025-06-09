package com.clinic.vet_clinic.user.mapper;

import com.clinic.vet_clinic.user.dto.UserResponseDTO;
import com.clinic.vet_clinic.user.model.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponseDTO toDTO(UserModel model) {
        return new UserResponseDTO(
                model.getId(),
                model.getUsername(),
                model.getEmail(),
                model.getPhone(),
                model.getRole()
        );
    }
}