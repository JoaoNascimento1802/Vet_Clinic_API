// ConsultationMapper.java
package com.clinic.vet_clinic.consultation.mapper;

import com.clinic.vet_clinic.clinic.repository.ClinicRepository;
import com.clinic.vet_clinic.consultation.dto.ConsultationRequestDTO;
import com.clinic.vet_clinic.consultation.dto.ConsultationResponseDTO;
import com.clinic.vet_clinic.consultation.model.ConsultationModel;
import com.clinic.vet_clinic.pet.repository.PetRepository;
import com.clinic.vet_clinic.user.repository.UserRepository;
import com.clinic.vet_clinic.veterinary.repository.VeterinaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ConsultationMapper {

    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final VeterinaryRepository veterinaryRepository;
    private final ClinicRepository clinicRepository;

    public ConsultationModel toModel(ConsultationRequestDTO dto) {
        return ConsultationModel.builder()
                .consultationdate(dto.consultationdate())
                .consultationtime(dto.consultationtime())
                .specialityEnum(dto.specialityEnum())
                .status(dto.status())
                .reason(dto.reason())
                .observations(dto.observations())
                .pet(petRepository.findById(dto.petId()).orElseThrow())
                .usuario(userRepository.findById(dto.usuarioId()).orElseThrow())
                .veterinario(veterinaryRepository.findById(dto.veterinarioId()).orElseThrow())
                .clinica(clinicRepository.findById(dto.clinicaId()).orElseThrow())
                .build();
    }

    public ConsultationResponseDTO toDTO(ConsultationModel model) {
        return new ConsultationResponseDTO(
                model.getId(),
                model.getConsultationdate(),
                model.getConsultationtime(),
                model.getSpecialityEnum(),
                model.getStatus(),
                model.getReason(),
                model.getObservations(),
                model.getPet().getId(),
                model.getUsuario().getId(),
                model.getVeterinario().getId(),
                model.getClinica().getId(),
                model.getDataCriacao(),
                model.getDataAtualizacao()
        );
    }
}
