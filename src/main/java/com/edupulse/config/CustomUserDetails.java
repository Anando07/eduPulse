package com.edupulse.config;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.edupulse.entity.Role;
import com.edupulse.entity.User;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    // Map roles to authorities
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles()
                   .stream()
                   .map(Role::getName) // get role name like "ADMINISTRATOR", "USER"
                   .map(roleName -> new SimpleGrantedAuthority(roleName)) // convert to GrantedAuthority
                   .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled(); // use the enabled flag from User entity
    }

    // Optional getter for the User entity
    public User getUser() {
        return this.user;
    }
}