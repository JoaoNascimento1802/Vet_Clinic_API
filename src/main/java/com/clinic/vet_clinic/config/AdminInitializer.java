package com.clinic.vet_clinic.config;


import com.clinic.vet_clinic.user.model.UserModel;
import com.clinic.vet_clinic.user.repository.UserRepository;
import com.clinic.vet_clinic.user.role.UserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
public class AdminInitializer {

    @Bean
    public CommandLineRunner initAdminUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // ======== FAÇA A MUDANÇA TEMPORÁRIA AQUI ========
            final String adminUsername = "meu-admin";                 // <-- Mude para um novo nome
            final String adminEmail = "meuadmin@vetclinic.com";     // <-- Mude para um novo email
            final String adminPassword = "MinhaSenhaAdmin@123";    // <-- Coloque uma nova senha forte
            // ======================================================

            // O resto do código pode continuar igual
            final String adminPhone = "11900000000";
            final String adminAddress = "Rua Admin, 123";
            final String adminRg = "987654321";
            final String adminImageUrl = "https://i.pravatar.cc/150";

            // O código abaixo vai verificar se "meu-admin" existe. Como não existe, ele vai criar.
            Optional<UserModel> adminUser = userRepository.findByUsername(adminUsername);

            if (adminUser.isEmpty()) {
                System.out.println("Usuário '" + adminUsername + "' não encontrado. Criando usuário administrador...");

                UserModel newAdmin = new UserModel();
                newAdmin.setUsername(adminUsername);
                newAdmin.setEmail(adminEmail);
                newAdmin.setPassword(passwordEncoder.encode(adminPassword));
                newAdmin.setPhone(adminPhone);
                newAdmin.setAddress(adminAddress);
                newAdmin.setRg(adminRg);
                newAdmin.setImageurl(adminImageUrl);
                newAdmin.setRole(UserRole.ADMIN); // Garante que a role é ADMIN

                userRepository.save(newAdmin);
                System.out.println("Usuário '" + adminUsername + "' criado com sucesso!");

            } else {
                System.out.println("Usuário '" + adminUsername + "' já existe.");
            }
        };
    }
}