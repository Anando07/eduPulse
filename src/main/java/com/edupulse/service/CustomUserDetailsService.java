package com.edupulse.service;

import com.edupulse.entity.User;
import com.edupulse.repository.UserRepository;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

      // inside loadUserByUsername()
        return org.springframework.security.core.userdetails.User.builder()
        .username(user.getEmail())
        .password(user.getPassword())
        .authorities(
            user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())) // convert to GrantedAuthority
                .toList()
        )
        .accountLocked(false)
        .disabled(!user.isEnabled()) // optional: disable if not enabled
        .build();
    }
}