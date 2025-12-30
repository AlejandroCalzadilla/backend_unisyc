package com.si2.parcial2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.si2.parcial2.entities.User;
import com.si2.parcial2.repositories.UserRepository;
import java.util.Optional;

@Component
public class UserSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        // Crear usuarios de ejemplo si no existen
        createUserIfNotExists("admin", "admin123", "admin@unisys.com", true);
        createUserIfNotExists("profesor", "profesor123", "profesor@unisys.com", false);
        createUserIfNotExists("estudiante", "estudiante123", "estudiante@unisys.com", false);
    }

    private void createUserIfNotExists(String username, String password, String email, boolean isAdmin) {
        if (!userRepository.existsByUsername(username)) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password); // Spring Security lo hasheará automáticamente
            user.setEmail(email);
            user.setAdmin(isAdmin);
            
            userRepository.save(user);
            System.out.println("✅ Usuario creado: " + username);
        } else {
            System.out.println("ℹ️  Usuario ya existe: " + username);
        }
    }
}
