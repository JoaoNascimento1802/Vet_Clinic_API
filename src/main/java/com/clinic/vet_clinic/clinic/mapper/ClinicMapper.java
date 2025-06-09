package com.clinic.vet_clinic.clinic.mapper;

import com.clinic.vet_clinic.clinic.dto.ClinicRequestDTO; // Importe o novo DTO
import com.clinic.vet_clinic.clinic.dto.ClinicResponseDTO;
import com.clinic.vet_clinic.clinic.model.ClinicModel;
import org.springframework.stereotype.Component;

@Component
public class ClinicMapper {

    // Novo método para converter DTO de requisição para Entidade
    public ClinicModel toModel(ClinicRequestDTO requestDTO) {
        return ClinicModel.builder()
                .name(requestDTO.name())
                .email(requestDTO.email())
                .phone(requestDTO.phone())
                .address(requestDTO.address())
                .careServices(requestDTO.careServices())
                .imageurl(requestDTO.imageurl())
                .build();
    }

    public ClinicResponseDTO toDTO(ClinicModel clinicModel) {
        return new ClinicResponseDTO(
                clinicModel.getId(),
                clinicModel.getName(),
                clinicModel.getEmail(),
                clinicModel.getPhone(),
                clinicModel.getAddress(),
                clinicModel.getCareServices(),
                clinicModel.getImageurl()
        );
    }
}