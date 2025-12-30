package com.si2.parcial2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.si2.parcial2.entities.Role;
import com.si2.parcial2.repositories.RoleRepository;

@Component
@Order(1) // Ejecutar primero
public class RoleSeeder implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        createRoleIfNotExists("ROLE_USER");
        createRoleIfNotExists("ROLE_ADMIN");
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
