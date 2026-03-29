package com.edupulse.service;

import com.edupulse.dto.UserDTO;
import com.edupulse.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User registerUser(UserDTO userDTO);
    Optional<User> findByEmail(String email);
    List<User> getAllUsers();
}