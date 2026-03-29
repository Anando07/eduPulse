package com.edupulse.controller;

import com.edupulse.dto.UserDTO;
import com.edupulse.entity.Role;
import com.edupulse.entity.User;
import com.edupulse.service.RoleService;
import com.edupulse.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class HomeController {

    private final UserService userService;
    private final RoleService roleService;

    public HomeController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    // ===== Public Pages =====
    @GetMapping({"/", "/home"})
    public String home() {
        return "pages/home";
    }

    @GetMapping("/about")
    public String about() {
        return "pages/about";
    }

    @GetMapping("/news")
    public String news() {
        return "pages/news";
    }

    // ===== Authentication Pages =====
    @GetMapping("/login")
    public String login() {
        return "pages/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("userDto", new UserDTO());
        return "pages/register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("userDto") UserDTO userDto,
                               BindingResult result,
                               Model model) {

        if (result.hasErrors()) {
            return "pages/register";
        }

        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            model.addAttribute("passwordError", "Passwords do not match");
            return "pages/register";
        }

        if (userService.existsByEmail(userDto.getEmail())) {
            model.addAttribute("emailError", "Email already exists");
            return "pages/register";
        }

        // Convert DTO to User entity
        User user = userService.convertToEntity(userDto);

        // Initialize roles if null
        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
        }

        // Assign default ROLE_USER
        Role role = roleService.findByName("ROLE_USER"); // Must match DB role name
        user.getRoles().add(role);

        // Save user
        userService.saveUser(user);

        model.addAttribute("successMessage", "Registration successful. Please login.");
        return "pages/login";
    }

    // ===== Dashboard =====
    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("user", user);

        // Role-based menu
        List<String> roles = user.getRoles().stream().map(Role::getName).toList();
        model.addAttribute("roles", roles);

        model.addAttribute("content", "pages/dashboard :: dashboardContent");
        model.addAttribute("pageTitle", "Dashboard");
        return "layout";
    }

    // ===== Profile =====
    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("user", user);
        return "pages/profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute("user") User user, Model model, Principal principal) {
        User existingUser = userService.findByEmail(principal.getName());

        // Update only allowed fields
        existingUser.setName(user.getName());
        existingUser.setPhone(user.getPhone());
        existingUser.setBloodGroup(user.getBloodGroup());
        existingUser.setPresentAddress(user.getPresentAddress());
        existingUser.setPermanentAddress(user.getPermanentAddress());
        existingUser.setJobType(user.getJobType());
        existingUser.setDesignation(user.getDesignation());

        // Update academic info
        existingUser.setSscInstitution(user.getSscInstitution());
        existingUser.setSscGroup(user.getSscGroup());
        existingUser.setSscRoll(user.getSscRoll());
        existingUser.setSscPassingYear(user.getSscPassingYear());

        existingUser.setHscInstitution(user.getHscInstitution());
        existingUser.setHscGroup(user.getHscGroup());
        existingUser.setHscRoll(user.getHscRoll());
        existingUser.setHscPassingYear(user.getHscPassingYear());

        existingUser.setUniversityInstitution(user.getUniversityInstitution());
        existingUser.setUniversitySubject(user.getUniversitySubject());
        existingUser.setUniversityRoll(user.getUniversityRoll());
        existingUser.setUniversityPassingYear(user.getUniversityPassingYear());

        existingUser.setBatch(user.getBatch());

        // Save updated user
        userService.updateUser(existingUser);

        model.addAttribute("successMessage", "Profile updated successfully");
        model.addAttribute("user", existingUser);

        return "pages/profile";
    }

    // ===== Error Page =====
    @GetMapping("/403")
    public String accessDenied() {
        return "pages/403";
    }
}