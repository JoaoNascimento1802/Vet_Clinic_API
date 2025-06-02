package com.clinic.vet_clinic.user.model;

import com.clinic.vet_clinic.consultation.model.ConsultationModel;
import com.clinic.vet_clinic.pet.model.PetModel;
import com.clinic.vet_clinic.user.role.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;


import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 50)
    @Column(nullable = false)
    private String username;

    @NotBlank
    @Size(min = 3 , max = 30)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).+$", message = "A senha deve conter pelo menos um número, uma letra maiúscula, uma letra minúscula e um caractere especial.")
    @Column(nullable = false)
    private String password;

    @Email
    @NotBlank
    @Size(max = 100)
    @Column(unique = true , nullable = false)
    private String email;

    @NotBlank
    @Pattern(regexp = "^\\d{2}\\d{8,9}$", message = "Formato de telefone inválido (ex: 11987654321)")
    @Column(unique = true , nullable = false)
    private String phone;

    @NotBlank
    @Size(max = 200)
    @Column(nullable = false)
    private String address;

    @NotBlank
    @Pattern(regexp = "^\\d{7,9}X?$", message = "Formato de RG inválido")
    @Column(unique = true , nullable = false)
    private String rg;

    @NotBlank
    @Column(nullable = false)
    private String imageurl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "usuario" ,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConsultationModel> consultas;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PetModel> pets;


}
