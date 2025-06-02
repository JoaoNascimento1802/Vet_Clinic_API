package com.clinic.vet_clinic.consultation.repository;

import com.clinic.vet_clinic.consultation.model.ConsultationModel;
import com.clinic.vet_clinic.veterinary.speciality.SpecialityEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;


public interface ConsultationRepository extends JpaRepository<ConsultationModel, Long> {
    // Pesquisar por data da consulta
    List<ConsultationModel> findByConsultationdate(LocalDate consultationDate);

    // Pesquisar por especialidade do médico
    List<ConsultationModel> findBySpecialityEnum(SpecialityEnum speciality);

    // Pesquisar por nome do médico (usando JOIN com VeterinaryModel)
    @Query("SELECT c FROM ConsultationModel c JOIN c.veterinario v WHERE v.name LIKE %:veterinaryName%")
    List<ConsultationModel> findByVeterinaryNameContaining(@Param("veterinaryName") String veterinaryName);

    // Pesquisar por nome do paciente (usando JOIN com PetModel)
    @Query("SELECT c FROM ConsultationModel c JOIN c.pet p WHERE p.name LIKE %:petName%")
    List<ConsultationModel> findByPetNameContaining(@Param("petName") String petName);

    // Você também pode querer combinar filtros, por exemplo:
    List<ConsultationModel> findByConsultationdateAndSpecialityEnum(LocalDate consultationDate, SpecialityEnum speciality);
}
