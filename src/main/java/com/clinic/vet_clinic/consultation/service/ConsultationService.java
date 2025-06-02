
package com.clinic.vet_clinic.consultation.service;

import com.clinic.vet_clinic.consultation.dto.ConsultationRequestDTO;
import com.clinic.vet_clinic.consultation.dto.ConsultationResponseDTO;
import com.clinic.vet_clinic.consultation.mapper.ConsultationMapper;
import com.clinic.vet_clinic.consultation.model.ConsultationModel;
import com.clinic.vet_clinic.consultation.repository.ConsultationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConsultationService {

    private final ConsultationRepository repository;
    private final ConsultationMapper mapper;

    public ConsultationResponseDTO save(ConsultationRequestDTO dto) {
        ConsultationModel model = mapper.toModel(dto);
        return mapper.toDTO(repository.save(model));
    }

    public List<ConsultationResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public ConsultationResponseDTO findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow();
    }

    public ConsultationResponseDTO update(Long id, ConsultationRequestDTO dto) {
        ConsultationModel existing = repository.findById(id).orElseThrow();
        ConsultationModel updated = mapper.toModel(dto);
        updated.setId(existing.getId());
        return mapper.toDTO(repository.save(updated));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
