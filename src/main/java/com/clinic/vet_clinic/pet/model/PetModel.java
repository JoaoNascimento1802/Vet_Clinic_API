package com.clinic.vet_clinic.pet.model;

import com.clinic.vet_clinic.consultation.model.ConsultationModel;
import com.clinic.vet_clinic.pet.breed.*;
import com.clinic.vet_clinic.pet.gender.Gender;
import com.clinic.vet_clinic.pet.species.Species;
import com.clinic.vet_clinic.user.model.UserModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "pets")
public class PetModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3 , max = 50)
    @Column(nullable = false)
    private String name;

    @Min(0)
    private int age;


    private String personalizatedSpecies;


    private String personalizedBreed;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Species speciespet;

    @NotBlank
    @Column(nullable = false)
    private String imageurl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private BirdBreed birdBreed;

    @Enumerated(EnumType.STRING)
    private CatBreed catBreed;

    @Enumerated(EnumType.STRING)
    private DogBreed dogBreed;

    @Enumerated(EnumType.STRING)
    private FishBreed fishBreed;

    @Enumerated(EnumType.STRING)
    private RabbitBreed rabbitBreed;

    @Enumerated(EnumType.STRING)
    private ReptileBreed reptileBreed;

    @Enumerated(EnumType.STRING)
    private RodentBreed rodentBreed;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConsultationModel> consultas;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UserModel usuario;



}



