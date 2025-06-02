package com.clinic.vet_clinic.veterinary.repository;

import com.clinic.vet_clinic.veterinary.model.VeterinaryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VeterinaryRepository extends JpaRepository<VeterinaryModel,Long> {
}
