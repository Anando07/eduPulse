package com.edupulse.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.edupulse.entity.User;
import com.edupulse.repository.UserRepository;

import java.util.Optional;

@Component
public class CustomsUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(username);
        
        // Use Optional's orElseThrow to handle user not found exception
        User user = optionalUser.orElseThrow(() -> 
            new UsernameNotFoundException("Could not find user with email: " + username)
        );

        // Check if the user is enabled
        if (!user.isEnabled()) {
            throw new DisabledException("User is not active");
        }
        
        return new CustomUserDetails(user);
    }
}
