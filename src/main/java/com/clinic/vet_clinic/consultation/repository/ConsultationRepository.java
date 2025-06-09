package com.clinic.vet_clinic.consultation.repository;

import com.clinic.vet_clinic.consultation.model.ConsultationModel;
import com.clinic.vet_clinic.veterinary.speciality.SpecialityEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.time.LocalTime; // Importe LocalTime
import java.util.List;

public interface ConsultationRepository extends JpaRepository<ConsultationModel, Long> {

    // Busca por ID do Veterinário
    List<ConsultationModel> findByVeterinarioId(Long veterinarioId);

    // Busca por ID do Veterinário E Especialidade
    List<ConsultationModel> findByVeterinarioIdAndSpecialityEnum(Long veterinarioId, SpecialityEnum specialityEnum);

    // Para buscar por data da consulta
    List<ConsultationModel> findByConsultationdate(LocalDate date);

    // Para buscar pela especialidade do enum
    List<ConsultationModel> findBySpecialityEnum(SpecialityEnum speciality);

    // Para buscar pelo nome do veterinário (navegando pela relação)
    // O nome do método significa: "Busque em ConsultationModel, pelo campo 'veterinario',
    // dentro dele pelo campo 'name', que contenha o texto fornecido, ignorando maiúsculas/minúsculas"
    List<ConsultationModel> findByVeterinario_NameContainingIgnoreCase(String veterinaryName);

    // Para buscar pelo nome do pet (navegando pela relação)
    List<ConsultationModel> findByPet_NameContainingIgnoreCase(String petName);


    // --- MÉTODO PARA A VALIDAÇÃO DE CONFLITO DE HORÁRIO ---
    boolean existsByVeterinarioIdAndConsultationdateAndConsultationtime(Long veterinarioId, LocalDate date, LocalTime time);
}