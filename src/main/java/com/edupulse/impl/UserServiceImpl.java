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

import java.time.LocalDateTime;
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

        if (existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            throw new RuntimeException("Password and Confirm Password do not match");
        }

        User user = convertToEntity(userDTO);

        // Assign default role
        Role role = roleService.findByName("ROLE_ALUMNI")
                .orElseGet(() -> roleService.saveRole(new Role("ROLE_ALUMNI")));

        user.setRoles(new HashSet<>(Set.of(role)));

        // Default flags
        user.setEmailVerified(false);
        user.setPhoneVerified(false);
        user.setEnabled(true); // make true if user can login immediately

        return userRepository.save(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setPhone(userDTO.getPhone());
        user.setBloodGroup(userDTO.getBloodGroup());

        user.setSscBoard(userDTO.getSscBoard());
        user.setSscInstitution(userDTO.getSscInstitution());
        user.setSscGroup(userDTO.getSscGroup());
        user.setSscRoll(userDTO.getSscRoll());
        user.setSscPassingYear(userDTO.getSscPassingYear());

        user.setHscBoard(userDTO.getHscBoard());
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

        return user;
    }

    @Override
    public User saveUser(User user) {
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

    // ================= DASHBOARD COUNTS =================
    @Override
    public long countAllUsers() {
        return userRepository.count();
    }

    @Override
    public long countActiveUsers() {
        return userRepository.countByEnabledTrue();
    }

    @Override
    public long countNewUsers() {
        LocalDateTime last7Days = LocalDateTime.now().minusDays(7);
        return userRepository.countByCreatedAtAfter(last7Days);
    }
}