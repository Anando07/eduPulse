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

import java.util.HashSet;

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

        // Convert DTO → Entity
        User user = userService.convertToEntity(userDto);

        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
        }

        // FIXED HERE
        Role role = roleService.getRoleByName("ROLE_USER");
        user.getRoles().add(role);

        userService.saveUser(user);

        model.addAttribute("successMessage", "Registration successful. Please login.");
        return "pages/login";
    }

    // ===== Error Page =====
    @GetMapping("/403")
    public String accessDenied() {
        return "pages/403";
    }
}