package com.si2.parcial2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import com.si2.parcial2.entities.User;
import com.si2.parcial2.repositories.UserRepository;
import com.si2.parcial2.services.UserService;

@Component
public class UserSeeder {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @EventListener(ApplicationReadyEvent.class)
    public void seedUsers() {
        // Crear usuarios de ejemplo si no existen
        createUserIfNotExists("admin", "admin123", "admin@unisys.com", true);
        createUserIfNotExists("profesor", "profesor123", "profesor@unisys.com", false);
        createUserIfNotExists("estudiante", "estudiante123", "estudiante@unisys.com", false);
    }

    private void createUserIfNotExists(String username, String password, String email, boolean isAdmin) {
        if (!userRepository.existsByUsername(username)) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
          
            user.setAdmin(isAdmin);
            
            userService.save(user); // Usa UserService para asignar roles automáticamente
            System.out.println("✅ Usuario creado: " + username + (isAdmin ? " (ADMIN)" : " (USER)"));
        } else {
            System.out.println("ℹ️  Usuario ya existe: " + username);
        }
    }
}
    
