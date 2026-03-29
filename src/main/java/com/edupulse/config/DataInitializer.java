package com.edupulse.config;

import com.edupulse.entity.Role;
import com.edupulse.entity.User;
import com.edupulse.repository.RoleRepository;
import com.edupulse.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(RoleRepository roleRepo,
                               UserRepository userRepo,
                               PasswordEncoder passwordEncoder) {
        return args -> {

            // ================= CREATE ROLES =================
            String[] roleNames = {
                    "ROLE_ADMINISTRATOR",
                    "ROLE_ADMIN",
                    "ROLE_USER",
                    "ROLE_ALUMNI",
                    "ROLE_OPERATOR",
                    "ROLE_VISITOR"
            };

            for (String roleName : roleNames) {
                roleRepo.findByName(roleName).orElseGet(() -> {
                    Role role = new Role();
                    role.setName(roleName);
                    return roleRepo.save(role);
                });
            }

            // ================= CREATE ADMIN USER =================
            if (userRepo.findByEmail("admin.lgac@gmail.com").isEmpty()) {

                User admin = new User();
                admin.setName("Super Admin");
                admin.setEmail("admin.lgac@gmail.com");
                admin.setPassword(passwordEncoder.encode("Admin@2005"));
                admin.setEnabled(true);
                admin.setEmailVerified(true);

                // Assign ADMINISTRATOR role
                Role adminRole = roleRepo.findByName("ROLE_ADMINISTRATOR").get();
                Set<Role> roles = new HashSet<>();
                roles.add(adminRole);

                admin.setRoles(roles);

                userRepo.save(admin);

                System.out.println("Default Admin Created!");
            }
        };
    }
}