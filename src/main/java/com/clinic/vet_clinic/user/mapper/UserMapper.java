package com.clinic.vet_clinic.user.mapper;

import com.clinic.vet_clinic.user.dto.UserRequestDTO;
import com.clinic.vet_clinic.user.dto.UserResponseDTO;
import com.clinic.vet_clinic.user.model.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    // NOVO MÉTODO: Converte o DTO de requisição para a entidade do banco
    public UserModel toModel(UserRequestDTO dto) {
        return UserModel.builder()
                .username(dto.username())
                .password(dto.password()) // A senha será codificada no controller
                .email(dto.email())
                .phone(dto.phone())
                .address(dto.address())
                .rg(dto.rg())
                .imageurl(dto.imageurl())
                .build();
    }

    // Este método já existia e está correto
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