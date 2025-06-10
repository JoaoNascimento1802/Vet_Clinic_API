package com.clinic.vet_clinic.pet.repository;

import com.clinic.vet_clinic.pet.model.PetModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PetRepository extends JpaRepository<PetModel,Long> {
}
