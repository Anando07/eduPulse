package com.edupulse.service;

import com.edupulse.dto.UserDTO;
import com.edupulse.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    // Already exists
    User registerUser(UserDTO userDTO);

    Optional<User> findByEmail(String email);

    List<User> getAllUsers();

    // ✅ ADD THESE METHODS
    boolean existsByEmail(String email);

    User convertToEntity(UserDTO userDTO);

    User saveUser(User user);
}