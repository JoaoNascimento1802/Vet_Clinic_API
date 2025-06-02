package com.clinic.vet_clinic.veterinary.dto;

import com.clinic.vet_clinic.veterinary.model.VeterinaryModel;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VeterinaryResponseDTO {

    private Long id;
    private String email;
    private String name;
    private String password;
    private String crmv;
    private String especialityenum;
    private String phone;
    private String imageurl;

    public static VeterinaryResponseDTO fromModel(VeterinaryModel veterinaryModel){
        return VeterinaryResponseDTO.builder()
                .id(veterinaryModel.getId())
                .name(veterinaryModel.getName())
                .email(veterinaryModel.getEmail())
                .phone(veterinaryModel.getPhone())
                .imageurl(veterinaryModel.getImageurl())
                .crmv(veterinaryModel.getCrmv())
                .password(veterinaryModel.getPassword())
                .especialityenum(veterinaryModel.getSpecialityenum().getDescricao()) // obter a descrição do Enum
                .build();
    }
}
