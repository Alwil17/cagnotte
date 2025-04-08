package com.grey.cagnotte.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.grey.cagnotte.entity.Permission;
import com.grey.cagnotte.entity.Role;
import com.grey.cagnotte.entity.User;
import com.grey.cagnotte.exception.CagnotteCustomException;
import com.grey.cagnotte.payload.request.UserRequest;
import com.grey.cagnotte.payload.response.UserResponse;
import com.grey.cagnotte.repository.PermissionRepository;
import com.grey.cagnotte.repository.RoleRepository;
import com.grey.cagnotte.repository.UserRepository;
import com.grey.cagnotte.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {
    private final String NOT_FOUND = "USER_NOT_FOUND";


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public long addUser(UserRequest userRequest) {
        log.info("UserServiceImpl | addUser is called");

        User user;
        if(!userRepository.existsByEmailEquals(userRequest.getEmail())){
            user = User.builder()
                    .lastname(userRequest.getLastname())
                    .firstname(userRequest.getFirstname())
                    .username(userRequest.getUsername())
                    .email(userRequest.getEmail())
                    .tel1(userRequest.getTel1())
                    .tel2(userRequest.getTel2())
                    .address(userRequest.getAddress())
                    .build();

            if(userRequest.getPassword() != null && !userRequest.getPassword().isBlank()){
                user.setPassword_hash(passwordEncoder.encode(userRequest.getPassword()));
            }

            //user = userRepository.save(user);

            if(userRequest.getRoles() != null && !userRequest.getRoles().isEmpty()){
                List<Role> roles = new ArrayList<>();
                userRequest.getRoles().forEach(permissionRequest -> {
                    // Traiter le cas où la permission n'est pas trouvée
                    roleRepository.findByName(permissionRequest.getName())
                            .ifPresentOrElse(
                                    roles::add, // Ajouter la permission si elle existe
                                    () -> {
                                        throw new RuntimeException("Role non trouvé: " + permissionRequest.getName());
                                    }
                            );
                });
                if (!roles.isEmpty()) {
                    user.setRoles(roles);
                    log.info(roles.size());
                    //user = userRepository.save(user);
                }
            }

            if(userRequest.getPermissions() != null && !userRequest.getPermissions().isEmpty()){
                List<Permission> permissions = new ArrayList<>();
                userRequest.getPermissions().forEach(permissionRequest -> {
                    // Traiter le cas où la permission n'est pas trouvée
                    permissionRepository.findByTitle(permissionRequest.getTitle())
                            .ifPresentOrElse(
                                    permissions::add, // Ajouter la permission si elle existe
                                    () -> {
                                        throw new RuntimeException("Permission non trouvée: " + permissionRequest.getTitle());
                                    }
                            );
                });
                if (!permissions.isEmpty()) {
                    user.setPermissions(permissions);
                    log.info(permissions.size());
                }
            }


            user = userRepository.save(user);
        }else{
            user = userRepository.findByEmailEquals(userRequest.getEmail()).orElseThrow();
            editUser(userRequest, user.getId());
        }

        log.info("UserServiceImpl | addUser | User Created | Id : " + user.getId());
        return user.getId();
    }

    @Override
    public void addUser(List<UserRequest> userRequests) {
        log.info("UserServiceImpl | addUser is called");

        for (UserRequest userRequest: userRequests) {
            User user;
            if(!userRepository.existsByEmailEquals(userRequest.getEmail())){
                user = User.builder()
                        .lastname(userRequest.getLastname())
                        .firstname(userRequest.getFirstname())
                        .username(userRequest.getUsername())
                        .email(userRequest.getEmail())
                        .tel1(userRequest.getTel1())
                        .tel2(userRequest.getTel2())
                        .address(userRequest.getAddress())
                        .build();

                if(userRequest.getPassword() != null && !userRequest.getPassword().isBlank()){
                    user.setPassword_hash(passwordEncoder.encode(userRequest.getPassword()));
                }

                if(userRequest.getRoles() != null && !userRequest.getRoles().isEmpty()){
                    List<Role> permissions = new ArrayList<>();
                    userRequest.getRoles().forEach(permissionRequest -> {
                        // Traiter le cas où la permission n'est pas trouvée
                        roleRepository.findByName(permissionRequest.getName())
                                .ifPresentOrElse(
                                        permissions::add, // Ajouter la permission si elle existe
                                        () -> {
                                            throw new RuntimeException("Role non trouvé: " + permissionRequest.getName());
                                        }
                                );
                    });
                    if (!permissions.isEmpty()) {
                        user.setRoles(permissions);
                        log.info(permissions.size());
                        //user = userRepository.save(user);
                    }
                }

                if(userRequest.getPermissions() != null && !userRequest.getPermissions().isEmpty()){
                    List<Permission> permissions = new ArrayList<>();
                    userRequest.getPermissions().forEach(permissionRequest -> {
                        // Traiter le cas où la permission n'est pas trouvée
                        permissionRepository.findByTitle(permissionRequest.getTitle())
                                .ifPresentOrElse(
                                        permissions::add, // Ajouter la permission si elle existe
                                        () -> {
                                            throw new RuntimeException("Permission non trouvée: " + permissionRequest.getTitle());
                                        }
                                );
                    });
                    if (!permissions.isEmpty()) {
                        user.setPermissions(permissions);
                        log.info(permissions.size());
                        //user = userRepository.save(user);
                    }
                }

                userRepository.save(user);
            }
        }

        log.info("UserServiceImpl | addUser | Users Created");
    }

    @Override
    public UserResponse getUserById(long userId) {
        log.info("UserServiceImpl | getUserById is called");
        log.info("UserServiceImpl | getUserById | Get the user for userId: {}", userId);

        User user
                = userRepository.findById(userId)
                .orElseThrow(
                        () -> new CagnotteCustomException("User with given Id not found", NOT_FOUND));

        return mapToResponse(user);
    }

    @Override
    public User getMe() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User userConnected = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();  // Le username du principal authentifié

        return userRepository.findByUsername(userConnected.getUsername()).orElseThrow();
    }

    @Override
    public UserResponse getUserByUsername(String username) {
        log.info("UserServiceImpl | getUserById is called");
        log.info("UserServiceImpl | getUserById | Get the user for username: {}", username);

        User user
                = userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new CagnotteCustomException("User with given username not found", NOT_FOUND));

        return mapToResponse(user);
    }

    @Override
    public UserResponse getUserByEmail(String userEmail) {
        log.info("UserServiceImpl | getUserByEmail is called");

        User user
                = userRepository.findByEmailEquals(userEmail)
                .orElseThrow(
                        () -> new CagnotteCustomException("User with given Email not found", NOT_FOUND));

        UserResponse userResponse = new UserResponse();

        copyProperties(user, userResponse);

        log.info("UserServiceImpl | getUserByEmail | userResponse :" + userResponse.toString());

        return userResponse;
    }

    @Override
    public void editUser(UserRequest userRequest, long userId) {
        log.info("UserServiceImpl | editUser is called");

        User user
                = userRepository.findById(userId)
                .orElseThrow(() -> new CagnotteCustomException(
                        "User with given Id not found",
                        NOT_FOUND
                ));
        user.setLastname(userRequest.getLastname());
        user.setFirstname(userRequest.getFirstname());
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setTel1(userRequest.getTel1());
        user.setTel2(userRequest.getTel2());
        user.setAddress(userRequest.getAddress());
        if(userRequest.getPassword() != null && !userRequest.getPassword().isBlank()){
            user.setPassword_hash(passwordEncoder.encode(userRequest.getPassword()));
        }
        userRepository.save(user);

        log.info("UserServiceImpl | editUser | User Updated | User Id :" + user.getId());
    }

    @Override
    public void deleteUserById(long userId) {
        log.info("User id: {}", userId);

        if (!userRepository.existsById(userId)) {
            log.info("Im in this loop {}", !userRepository.existsById(userId));
            throw new CagnotteCustomException(
                    "User with given with Id: " + userId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting User with id: {}", userId);
        userRepository.deleteById(userId);
    }

    public UserResponse mapToResponse(User user){
        UserResponse userResponse = new UserResponse();

        // Récupérer toutes les permissions des rôles
        List<Permission> rolePermissions = user.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .distinct()
                .collect(Collectors.toList());

        // Fusionner les permissions directes, des rôles, et des postes
        List<Permission> allPermissions = Stream.concat(user.getPermissions().stream(), rolePermissions.stream())
                .distinct()
                .collect(Collectors.toList());

        // Si nécessaire, attacher toutes les permissions à l'utilisateur
        user.setPermissions(allPermissions);

        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new JavaTimeModule());
        userResponse = mapper.convertValue(user, UserResponse.class);

        userResponse.setActive(user.isActive());

        return userResponse;
    }
}
