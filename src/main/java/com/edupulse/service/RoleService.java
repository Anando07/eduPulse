package com.edupulse.service;

import com.edupulse.entity.Role;
import java.util.Optional;

public interface RoleService {

    Role saveRole(Role role);

    Optional<Role> findByName(String name);

    //Helper method (recommended)
    default Role getRoleByName(String name) {
        return findByName(name)
                .orElseThrow(() -> new RuntimeException("Role not found: " + name));
    }
}