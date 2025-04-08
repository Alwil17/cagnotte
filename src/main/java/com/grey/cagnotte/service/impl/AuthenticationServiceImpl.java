package com.grey.cagnotte.service.impl;

import com.grey.cagnotte.entity.Role;
import com.grey.cagnotte.entity.User;
import com.grey.cagnotte.repository.UserRepository;
import com.grey.cagnotte.security.JwtTokenProvider;
import com.grey.cagnotte.service.AuthenticationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public String login(String username, String password) {
        log.info("entered login srvice");
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid username or password"));

        log.info("find user");
        if (!user.isActive()) {
            log.info("not active");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"User account is deactivated. Please contact the administrator.");
        }

        if (!passwordEncoder.matches(password, user.getPassword_hash())) {
            log.info("no match password");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid username or password");
        }
        log.info("Good");
        List<String> roles = user.getRoles().stream()
                .map(Role::getName) // Supposons que chaque rôle a un champ `name`
                .collect(Collectors.toList());
        user.setLast_access_time(LocalDateTime.now());
        user.setActive(true);
        userRepository.save(user);
        return jwtTokenProvider.createToken(user.getUsername(), roles);
    }

    @Override
    public User register(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username is already taken");
        }

        user.setPassword_hash(passwordEncoder.encode(user.getPassword()));
        user.setActive(true); // Activate user by default
        return userRepository.save(user);
    }
}
