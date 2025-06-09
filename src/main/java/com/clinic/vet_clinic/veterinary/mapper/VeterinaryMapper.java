package com.clinic.vet_clinic.veterinary.mapper;

import com.clinic.vet_clinic.veterinary.dto.VeterinaryRequestDTO;
import com.clinic.vet_clinic.veterinary.dto.VeterinaryResponseDTO;
import com.clinic.vet_clinic.veterinary.model.VeterinaryModel;
import org.springframework.stereotype.Component;

@Component
public class VeterinaryMapper {

    // NOVO MÉTODO PARA CONVERTER O REQUEST DTO EM ENTIDADE
    public VeterinaryModel toModel(VeterinaryRequestDTO requestDTO) {
        return VeterinaryModel.builder()
                .name(requestDTO.name())
                .email(requestDTO.email())
                .password(requestDTO.password()) // A senha será codificada no controller
                .crmv(requestDTO.crmv())
                .specialityenum(requestDTO.specialityenum())
                .phone(requestDTO.phone())
                .imageurl(requestDTO.imageurl())
                .build();
    }

    public VeterinaryResponseDTO toDTO(VeterinaryModel model) {
        return new VeterinaryResponseDTO(
                model.getId(),
                model.getName(),
                model.getEmail(),
                model.getCrmv(),
                model.getSpecialityenum(),
                model.getPhone(),
                model.getImageurl()
        );
    }
}
