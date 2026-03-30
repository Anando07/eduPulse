package com.edupulse.controller;

import com.edupulse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    private final UserService userService;

    // Constructor injection (preferred)
    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Set page title
        model.addAttribute("title", "Dashboard");
        model.addAttribute("page", "dashboard"); // must match fragment name

        // Dashboard statistics
        model.addAttribute("totalUsers", userService.countAllUsers());
        model.addAttribute("activeUsers", userService.countActiveUsers());
        model.addAttribute("newUsers", userService.countNewUsers());

        // Render base.html
        return "base";
    }
}