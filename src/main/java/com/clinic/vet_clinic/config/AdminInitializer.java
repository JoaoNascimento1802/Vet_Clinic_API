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

    // Este @Bean será executado automaticamente na inicialização da aplicação
    @Bean
    public CommandLineRunner initAdminUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Definições do usuário administrador
            final String adminUsername = "admin";
            final String adminEmail = "admin@vetclinic.com";
            // A senha deve seguir o padrão: pelo menos um número, uma letra maiúscula,
            // uma letra minúscula e um caractere especial.
            final String adminPassword = "Admin@Password123"; // ATENÇÃO: Mude esta senha em produção!
            final String adminPhone = "11912345678"; // Exemplo de telefone válido
            final String adminAddress = "Rua Principal, 100 - Centro";
            final String adminRg = "12345678X"; // Exemplo de RG válido
            final String adminImageUrl = "https://example.com/admin_default_profile.jpg"; // URL de imagem padrão ou null

            // Verifica se um usuário com o username 'admin' já existe
            Optional<UserModel> adminUser = userRepository.findByUsername(adminUsername);

            if (adminUser.isEmpty()) {
                System.out.println("Usuário 'admin' não encontrado. Criando usuário administrador...");

                UserModel newAdmin = new UserModel();
                newAdmin.setUsername(adminUsername);
                newAdmin.setEmail(adminEmail);
                newAdmin.setPassword(passwordEncoder.encode(adminPassword)); // Criptografa a senha!
                newAdmin.setPhone(adminPhone);
                newAdmin.setAddress(adminAddress);
                newAdmin.setRg(adminRg);
                newAdmin.setImageurl(adminImageUrl); // Define a URL da imagem
                newAdmin.setRole(UserRole.ADMIN); // Define a role como ADMIN (do seu enum UserRole)

                try {
                    userRepository.save(newAdmin);
                    System.out.println("Usuário 'admin' criado com sucesso!");
                } catch (Exception e) {
                    System.err.println("Erro ao salvar o usuário admin: " + e.getMessage());
                    // Isso pode ocorrer se houver uma violação de constraint (ex: RG/Email duplicado)
                    // ou se alguma validação for mais complexa e não for coberta pelos dados mockados.
                }

            } else {
                System.out.println("Usuário 'admin' já existe.");
            }
        };
    }
}
