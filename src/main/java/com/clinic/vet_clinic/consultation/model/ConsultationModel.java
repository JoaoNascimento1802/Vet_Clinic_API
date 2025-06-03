package com.clinic.vet_clinic.consultation.model;

import com.clinic.vet_clinic.clinic.model.ClinicModel;
import com.clinic.vet_clinic.consultation.status.ConsultationStatus;
import com.clinic.vet_clinic.pet.model.PetModel;
import com.clinic.vet_clinic.user.model.UserModel;
import com.clinic.vet_clinic.veterinary.model.VeterinaryModel;
import com.clinic.vet_clinic.veterinary.speciality.SpecialityEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "consultas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsultationModel {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;


        @JsonFormat(pattern = "yyyy-MM-dd")
        @Column(nullable = false)
        private LocalDate consultationdate;

        @JsonFormat(pattern = "HH:mm")
        @Column(nullable = false)
        private LocalTime consultationtime;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private SpecialityEnum specialityEnum;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private ConsultationStatus status;

        @Column(nullable = false)
        private String reason;

        @Column(nullable = false)
        private String observations;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "pet_id")
        private PetModel pet;

        @ManyToOne
        @JoinColumn(name = "usuario_id")
        private UserModel usuario;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "veterinario_id")
        private VeterinaryModel veterinario;

        @ManyToOne
        @JoinColumn(name = "clinica_id")
        private ClinicModel clinica;

        private LocalDateTime dataCriacao;

        private LocalDateTime dataAtualizacao;

        @PrePersist
        public void prePersist() {
            this.dataCriacao = LocalDateTime.now();
        }

        @PreUpdate
        public void preUpdate() {
            this.dataAtualizacao = LocalDateTime.now();
        }


    }

