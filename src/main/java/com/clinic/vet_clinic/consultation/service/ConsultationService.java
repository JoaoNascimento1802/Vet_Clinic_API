package com.clinic.vet_clinic.consultation.service;

import com.clinic.vet_clinic.consultation.dto.ConsultationRequestDTO;
import com.clinic.vet_clinic.consultation.dto.ConsultationResponseDTO;
import com.clinic.vet_clinic.consultation.mapper.ConsultationMapper;
import com.clinic.vet_clinic.consultation.model.ConsultationModel;
import com.clinic.vet_clinic.consultation.repository.ConsultationRepository;
import com.clinic.vet_clinic.veterinary.speciality.SpecialityEnum; // Import adicionado
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate; // Import adicionado
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
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada com o ID: " + id)); // Melhorar a exceção
    }

    public ConsultationResponseDTO update(Long id, ConsultationRequestDTO dto) {
        ConsultationModel existing = repository.findById(id).orElseThrow(() -> new RuntimeException("Consulta não encontrada para atualização com o ID: " + id));
        // Mapeie apenas os campos que podem ser atualizados.
        ConsultationModel updated = mapper.toModel(dto);
        updated.setId(existing.getId()); // Garante que o ID do registro existente seja mantido
        updated.setDataCriacao(existing.getDataCriacao()); // Preserva a data de criação
        // Se houver campos que não devem ser atualizados via DTO, ajuste o mapper ou defina-os aqui
        return mapper.toDTO(repository.save(updated));
    }


    public void delete(Long id) {
        if (!repository.existsById(id)) { // Verifica se existe antes de tentar deletar
            throw new RuntimeException("Consulta não encontrada para exclusão com o ID: " + id);
        }
        repository.deleteById(id);
    }

    // NOVOS MÉTODOS DE PESQUISA:

    public List<ConsultationResponseDTO> findConsultationsByDate(LocalDate date) {
        return repository.findByConsultationdate(date).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ConsultationResponseDTO> findConsultationsBySpeciality(SpecialityEnum speciality) {
        return repository.findBySpecialityEnum(speciality).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ConsultationResponseDTO> findConsultationsByVeterinaryName(String veterinaryName) {
        return repository.findByVeterinaryNameContaining(veterinaryName).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ConsultationResponseDTO> findConsultationsByPetName(String petName) {
        return repository.findByPetNameContaining(petName).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}