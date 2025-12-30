package com.si2.parcial2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
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
    
    private boolean usersInitialized = false;

    @EventListener(ContextRefreshedEvent.class)
    public void seedUsers() {
        if (usersInitialized) {
            return; // Solo ejecutar una vez
        }
        
        try {
            // Dar tiempo a Hibernate para crear las tablas
            Thread.sleep(12000);
            
            // Crear usuarios de ejemplo si no existen
            createUserIfNotExists("admin", "admin123", "admin@unisys.com", true);
            createUserIfNotExists("profesor", "profesor123", "profesor@unisys.com", false);
            createUserIfNotExists("estudiante", "estudiante123", "estudiante@unisys.com", false);
            usersInitialized = true;
        } catch (Exception e) {
            System.err.println("⚠️  Error al crear usuarios (reintentar en siguiente inicio): " + e.getMessage());
        }
    }

    private void createUserIfNotExists(String username, String password, String email, boolean isAdmin) {
        try {
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
        } catch (Exception e) {
            System.err.println("Error creando usuario " + username + ": " + e.getMessage());
        }
    }
}
    
