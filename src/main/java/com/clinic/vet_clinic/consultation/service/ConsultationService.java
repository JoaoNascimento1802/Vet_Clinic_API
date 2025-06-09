package com.clinic.vet_clinic.consultation.service;

import com.clinic.vet_clinic.clinic.model.ClinicModel;
import com.clinic.vet_clinic.clinic.repository.ClinicRepository;
import com.clinic.vet_clinic.consultation.dto.ConsultationRequestDTO;
import com.clinic.vet_clinic.consultation.dto.ConsultationResponseDTO;
import com.clinic.vet_clinic.consultation.mapper.ConsultationMapper;
import com.clinic.vet_clinic.consultation.model.ConsultationModel;
import com.clinic.vet_clinic.consultation.repository.ConsultationRepository;
import com.clinic.vet_clinic.pet.model.PetModel;
import com.clinic.vet_clinic.pet.repository.PetRepository;
import com.clinic.vet_clinic.user.model.UserModel;
import com.clinic.vet_clinic.user.repository.UserRepository;
import com.clinic.vet_clinic.veterinary.model.VeterinaryModel;
import com.clinic.vet_clinic.veterinary.repository.VeterinaryRepository;
import com.clinic.vet_clinic.veterinary.speciality.SpecialityEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConsultationService {

    // --- INJEÇÃO DE DEPENDÊNCIAS ---
    private final ConsultationRepository consultationRepository;
    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final VeterinaryRepository veterinaryRepository;
    private final ClinicRepository clinicRepository;
    private final ConsultationMapper consultationMapper;

    // --- MÉTODOS CRUD PRINCIPAIS ---

    /**
     * Cria uma nova consulta após validar as regras de negócio.
     */
    public ConsultationResponseDTO save(ConsultationRequestDTO dto) {
        // 1. VALIDAÇÃO: Verifica se já existe uma consulta no mesmo horário para o mesmo veterinário.
        boolean appointmentExists = consultationRepository.existsByVeterinarioIdAndConsultationdateAndConsultationtime(
                dto.veterinarioId(), dto.consultationdate(), dto.consultationtime());
        if (appointmentExists) {
            throw new IllegalStateException("Já existe uma consulta agendada para este veterinário neste horário.");
        }

        // 2. BUSCA DAS ENTIDADES
        UserModel user = userRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado com o ID: " + dto.usuarioId()));
        PetModel pet = petRepository.findById(dto.petId())
                .orElseThrow(() -> new NoSuchElementException("Pet não encontrado com o ID: " + dto.petId()));
        VeterinaryModel vet = veterinaryRepository.findById(dto.veterinarioId())
                .orElseThrow(() -> new NoSuchElementException("Veterinário não encontrado com o ID: " + dto.veterinarioId()));
        ClinicModel clinic = clinicRepository.findById(dto.clinicaId())
                .orElseThrow(() -> new NoSuchElementException("Clínica não encontrada com o ID: " + dto.clinicaId()));

        // 3. CRIAÇÃO DO MODELO
        ConsultationModel newConsultation = ConsultationModel.builder()
                .consultationdate(dto.consultationdate())
                .consultationtime(dto.consultationtime())
                .specialityEnum(dto.specialityEnum())
                .status(dto.status())
                .reason(dto.reason())
                .observations(dto.observations())
                .usuario(user)
                .pet(pet)
                .veterinario(vet)
                .clinica(clinic)
                .build();

        // 4. PERSISTÊNCIA E RESPOSTA
        ConsultationModel savedConsultation = consultationRepository.save(newConsultation);
        return consultationMapper.toDTO(savedConsultation);
    }

    /**
     * Atualiza uma consulta existente.
     */
    public ConsultationResponseDTO update(Long id, ConsultationRequestDTO dto) {
        // Garante que a consulta a ser atualizada existe
        ConsultationModel existingConsultation = consultationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Consulta não encontrada para atualização com o ID: " + id));

        // Busca as novas entidades (caso tenham sido alteradas)
        UserModel user = userRepository.findById(dto.usuarioId()).orElseThrow(() -> new NoSuchElementException("Usuário não encontrado."));
        PetModel pet = petRepository.findById(dto.petId()).orElseThrow(() -> new NoSuchElementException("Pet não encontrado."));
        VeterinaryModel vet = veterinaryRepository.findById(dto.veterinarioId()).orElseThrow(() -> new NoSuchElementException("Veterinário não encontrado."));
        ClinicModel clinic = clinicRepository.findById(dto.clinicaId()).orElseThrow(() -> new NoSuchElementException("Clínica não encontrada."));

        // Atualiza os campos da consulta existente
        existingConsultation.setConsultationdate(dto.consultationdate());
        existingConsultation.setConsultationtime(dto.consultationtime());
        existingConsultation.setSpecialityEnum(dto.specialityEnum());
        existingConsultation.setStatus(dto.status());
        existingConsultation.setReason(dto.reason());
        existingConsultation.setObservations(dto.observations());
        existingConsultation.setUsuario(user);
        existingConsultation.setPet(pet);
        existingConsultation.setVeterinario(vet);
        existingConsultation.setClinica(clinic);

        ConsultationModel updatedConsultation = consultationRepository.save(existingConsultation);
        return consultationMapper.toDTO(updatedConsultation);
    }

    /**
     * Deleta uma consulta por ID.
     */
    public void delete(Long id) {
        if (!consultationRepository.existsById(id)) {
            throw new NoSuchElementException("Consulta não encontrada para exclusão com o ID: " + id);
        }
        consultationRepository.deleteById(id);
    }

    // --- MÉTODOS DE BUSCA E PESQUISA ---

    public List<ConsultationResponseDTO> findAll() {
        return consultationRepository.findAll().stream()
                .map(consultationMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ConsultationResponseDTO findById(Long id) {
        return consultationRepository.findById(id)
                .map(consultationMapper::toDTO)
                .orElseThrow(() -> new NoSuchElementException("Consulta não encontrada com o ID: " + id));
    }

    public List<ConsultationResponseDTO> findConsultationsByDate(LocalDate date) {
        return consultationRepository.findByConsultationdate(date).stream()
                .map(consultationMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ConsultationResponseDTO> findConsultationsBySpeciality(SpecialityEnum speciality) {
        return consultationRepository.findBySpecialityEnum(speciality).stream()
                .map(consultationMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ConsultationResponseDTO> findConsultationsByVeterinaryName(String veterinaryName) {
        return consultationRepository.findByVeterinario_NameContainingIgnoreCase(veterinaryName).stream()
                .map(consultationMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ConsultationResponseDTO> findConsultationsByPetName(String petName) {
        return consultationRepository.findByPet_NameContainingIgnoreCase(petName).stream()
                .map(consultationMapper::toDTO)
                .collect(Collectors.toList());
    }
}