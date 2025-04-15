package com.grey.cagnotte.service.impl;

import com.grey.cagnotte.entity.User;
import com.grey.cagnotte.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Hibernate.initialize(user.getRoles());
        Hibernate.initialize(user.getPermissions()); // in case it's lazy

        List<GrantedAuthority> authorities = new ArrayList<>();

        if (user.getRoles() != null) {
            authorities.addAll(user.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                    .collect(Collectors.toList()));

            // In case permissions are associated with roles
            for (var role : user.getRoles()) {
                authorities.addAll(role.getPermissions().stream()
                        .map(permission -> new SimpleGrantedAuthority(permission.getTitle()))
                        .collect(Collectors.toList()));
            }
        }

        // User specific permissions (optional)
        if (user.getPermissions() != null) {
            authorities.addAll(user.getPermissions().stream()
                    .map(permission -> new SimpleGrantedAuthority(permission.getTitle()))
                    .collect(Collectors.toList()));
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }

}
