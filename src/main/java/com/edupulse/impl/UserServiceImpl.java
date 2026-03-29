package com.edupulse.impl;

import com.edupulse.dto.UserDTO;
import com.edupulse.entity.Role;
import com.edupulse.entity.User;
import com.edupulse.repository.UserRepository;
import com.edupulse.service.RoleService;
import com.edupulse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(UserDTO userDTO) {
        // Check if email exists
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        // Password match validation
        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            throw new RuntimeException("Password and Confirm Password do not match");
        }

        // Map DTO to entity
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setPhone(userDTO.getPhone());
        user.setBloodGroup(userDTO.getBloodGroup());
        user.setSscInstitution(userDTO.getSscInstitution());
        user.setSscGroup(userDTO.getSscGroup());
        user.setSscRoll(userDTO.getSscRoll());
        user.setSscPassingYear(userDTO.getSscPassingYear());
        user.setHscInstitution(userDTO.getHscInstitution());
        user.setHscGroup(userDTO.getHscGroup());
        user.setHscRoll(userDTO.getHscRoll());
        user.setHscPassingYear(userDTO.getHscPassingYear());
        user.setUniversityInstitution(userDTO.getUniversityInstitution());
        user.setUniversitySubject(userDTO.getUniversitySubject());
        user.setUniversityRoll(userDTO.getUniversityRoll());
        user.setUniversityPassingYear(userDTO.getUniversityPassingYear());
        user.setBatch(userDTO.getBatch());
        user.setPresentAddress(userDTO.getPresentAddress());
        user.setPermanentAddress(userDTO.getPermanentAddress());
        user.setJobType(userDTO.getJobType());
        user.setDesignation(userDTO.getDesignation());
        user.setProfileImage(userDTO.getProfileImage());

        // Assign default role ALUMNI
        Role alumniRole = roleService.findByName("ROLE_ALUMNI")
                .orElseGet(() -> roleService.saveRole(new Role("ROLE_ALUMNI")));

        Set<Role> roles = new HashSet<>();
        roles.add(alumniRole);
        user.setRoles(roles);

        // By default email/phone not verified
        user.setEmailVerified(false);
        user.setPhoneVerified(false);
        user.setEnabled(false);

        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
