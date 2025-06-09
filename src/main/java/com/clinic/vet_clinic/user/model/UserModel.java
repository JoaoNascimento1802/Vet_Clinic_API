package com.clinic.vet_clinic.user.model;

import com.clinic.vet_clinic.consultation.model.ConsultationModel;
import com.clinic.vet_clinic.pet.model.PetModel;
import com.clinic.vet_clinic.user.role.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore; // Importe esta anotação!
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class UserModel implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 50)
    @Column(nullable = false)
    private String username;

    @NotBlank
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

    @JsonManagedReference // <-- ADICIONE AQUI
    @OneToMany(mappedBy = "usuario" ,cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    private List<ConsultationModel> consultas;

    @JsonManagedReference // <-- ADICIONE AQUI
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PetModel> pets;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // O prefixo 'ROLE_' é um padrão do Spring Security
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        // O Spring Security usará este método, que deve retornar o identificador único.
        // No seu caso, é o email.
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Você pode adicionar lógica para expirar contas
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Você pode adicionar lógica para bloquear contas
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Você pode adicionar lógica para expirar credenciais
    }

    @Override
    public boolean isEnabled() {
        return true; // Você pode adicionar lógica para desabilitar contas
    }
}