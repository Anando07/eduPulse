package com.edupulse.service;

import com.edupulse.dto.UserDTO;
import com.edupulse.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User registerUser(UserDTO userDTO);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    User convertToEntity(UserDTO userDTO);

    User saveUser(User user);

    List<User> getAllUsers();

    // ✅ Dashboard counts
    long countAllUsers();
    long countActiveUsers();
    long countNewUsers();
}