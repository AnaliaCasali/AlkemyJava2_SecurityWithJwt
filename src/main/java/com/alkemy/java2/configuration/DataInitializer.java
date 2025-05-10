package com.alkemy.java2.configuration;

import com.alkemy.java2.model.Role;
import com.alkemy.java2.model.User;
import com.alkemy.java2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataInitializer {

    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository) {
        return args -> {
            try {
                // Verificar si la colección de usuarios está vacía
                if (userRepository.count() == 0) {
                    log.info("Inicializando datos de usuarios en la base de datos...");

                    // Usuario admin
                    User admin = createUser("admin@example.com", "Admin123!", true, Set.of(Role.ADMIN));
                    // Usuario normal
                    User user = createUser("user@example.com", "User123!", true, Set.of(Role.USER));

                    userRepository.save(admin);
                    userRepository.save(user);

                    log.info("✅ Usuarios iniciales creados exitosamente");
                    log.info("🔑 Credenciales de administrador: admin@example.com / Admin123!");
                    log.info("🔑 Credenciales de usuario normal: user@example.com / User123!");
                } else {
                    log.info("La base de datos ya contiene usuarios, omitiendo inicialización");
                }
            } catch (Exception e) {
                log.error("❌ Error al inicializar datos de usuarios", e);
            }
        };
    }

    private User createUser(String username, String password, boolean active , Set<Role> roles) {
        return User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .active(true)
                .roles(roles)
                .build();
    }
}