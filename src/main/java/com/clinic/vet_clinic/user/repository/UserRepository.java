package com.clinic.vet_clinic.user.repository;

import com.clinic.vet_clinic.user.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
}
