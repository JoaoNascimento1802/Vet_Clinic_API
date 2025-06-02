package com.clinic.vet_clinic.consultation.repository;

import com.clinic.vet_clinic.consultation.model.ConsultationModel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ConsultationRepository extends JpaRepository<ConsultationModel, Long> {
}
