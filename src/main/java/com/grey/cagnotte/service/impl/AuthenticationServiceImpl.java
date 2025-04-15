package com.grey.cagnotte.service.impl;

import com.grey.cagnotte.entity.Role;
import com.grey.cagnotte.entity.User;
import com.grey.cagnotte.exception.CagnotteCustomException;
import com.grey.cagnotte.payload.request.RegisterRequest;
import com.grey.cagnotte.payload.request.UserRequest;
import com.grey.cagnotte.payload.response.UserResponse;
import com.grey.cagnotte.repository.RoleRepository;
import com.grey.cagnotte.repository.UserRepository;
import com.grey.cagnotte.security.JwtTokenProvider;
import com.grey.cagnotte.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

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
    public User register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new CagnotteCustomException("Username is already taken", "USERNAME_ALREADY_TAKEN");
        }

        User user = User.builder()
                .username(request.getUsername())
                .lastname(request.getLastname())
                .firstname(request.getFirstname())
                .email(request.getEmail())
                .tel1(request.getTel1())
                .tel2(request.getTel2())
                .address(request.getAddress())
                .isActive(true) // Activate user by default
                .build();

        user.setPassword_hash(passwordEncoder.encode(request.getPassword()));

        Role role = roleRepository.findByName("USER").orElseThrow();
        user.setRoles(Collections.singletonList(role));

        return userRepository.save(user);
    }
}
