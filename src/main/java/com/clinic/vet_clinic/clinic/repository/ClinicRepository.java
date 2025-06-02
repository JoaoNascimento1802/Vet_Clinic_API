package com.clinic.vet_clinic.clinic.repository;

import com.clinic.vet_clinic.clinic.model.ClinicModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClinicRepository extends JpaRepository<ClinicModel, Long> {
}
