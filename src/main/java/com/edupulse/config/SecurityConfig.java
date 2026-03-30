package com.edupulse.config;

import com.edupulse.service.CustomUserDetailsService;

import jakarta.servlet.http.HttpSession;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // Password encoder bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AuthenticationManager bean
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // Security filter chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                // STATIC RESOURCES
                // Public pages
                 .requestMatchers("/", "/home", "/about", "/news", "/register", "/css/**", "/js/**").permitAll()
                
                // PUBLIC ENDPOINTS
                .requestMatchers(
                        "/", "/home", "/login", "/registration", "/do-register",
                        "/about", "/notice", "/team", "/comment", "/contact/**",
                        "/terms", "/article", "/privacy", "/associates", "/function",
                        "/activity", "/signup", "/saveUser", "/register/user",
                        "/blooddonor", "/forgetPassword", "/resetPassword", "/deleteToken"
                ).permitAll()

                // ROLE-BASED ENDPOINTS
                .requestMatchers("/profile").hasAnyAuthority("administrator", "admin", "user", "viewer")
                .requestMatchers("/administrator/**").hasAuthority("administrator")
                .requestMatchers("/admin/**").hasAnyAuthority("admin", "administrator")
                .requestMatchers("/user/**").hasAnyAuthority("administrator", "admin", "user")
                .requestMatchers("/viewer/**").hasAnyAuthority("administrator", "admin", "viewer")

                // ALL OTHER REQUESTS
                .anyRequest().authenticated()
            )
            // FORM LOGIN
            .formLogin(form -> form
                .loginPage("/index")
                .loginProcessingUrl("/do-login")
                .defaultSuccessUrl("/dashboard", true)
                .permitAll()
            )
            // LOGOUT
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessHandler((request, response, authentication) -> {
                    HttpSession session = request.getSession();
                    session.setAttribute("logoutSuccess", true);
                    response.sendRedirect("/login");
                })
                .permitAll()
            )
            // ACCESS DENIED HANDLER
            .exceptionHandling(ex -> ex
                .accessDeniedPage("/403")
            );

        return http.build();
    }

}