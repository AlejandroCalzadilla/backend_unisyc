package com.si2.parcial2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import com.si2.parcial2.entities.Role;
import com.si2.parcial2.repositories.RoleRepository;

@Component
public class RoleSeeder {

    @Autowired
    private RoleRepository roleRepository;
    
    private boolean rolesInitialized = false;

    @EventListener(ContextRefreshedEvent.class)
    public void seedRoles() {
        if (rolesInitialized) {
            return; // Solo ejecutar una vez
        }
        
        try {
            createRoleIfNotExists("ROLE_USER");
            createRoleIfNotExists("ROLE_ADMIN");
            rolesInitialized = true;
        } catch (Exception e) {
            System.err.println("Error al crear roles: " + e.getMessage());
        }
    }

    private void createRoleIfNotExists(String roleName) {
        if (roleRepository.findByName(roleName).isEmpty()) {
            Role role = new Role(roleName);
            roleRepository.save(role);
            System.out.println("✅ Rol creado: " + roleName);
        } else {
            System.out.println("ℹ️  Rol ya existe: " + roleName);
        }
    }
}
