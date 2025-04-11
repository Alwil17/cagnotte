package com.grey.cagnotte.controller;


import com.grey.cagnotte.entity.User;
import com.grey.cagnotte.payload.request.UserRequest;
import com.grey.cagnotte.payload.response.UserResponse;
import com.grey.cagnotte.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Log4j2
public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("UserController | getAllUsers is called");
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<Long> addUser(@RequestBody UserRequest userRequest) {

        log.info("UserController | addUser is called");

        log.info("UserController | addUser | userRequest : " + userRequest.toString());

        long userId = userService.addUser(userRequest);
        return new ResponseEntity<>(userId, HttpStatus.CREATED);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponse> getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User userConnected = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();  // Le username du principal authentifié

        UserResponse user = userService.getUserByUsername(userConnected.getUsername());
        return ResponseEntity.ok(user);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponse> getUserByEmail(@PathVariable("email") String userEmail) {
        log.info("UserController | getUserByEmail is called");
        log.info("UserController | getUserByEmail | userEmail : " + userEmail);

        UserResponse userResponse
                = userService.getUserByEmail(userEmail);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> editUser(@RequestBody UserRequest userRequest,
            @PathVariable("id") long userId
    ) {

        log.info("UserController | editUser is called");

        log.info("UserController | editUser | userId : " + userId);

        userService.editUser(userRequest, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DELETE_USER')")
    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable("id") long userId) {
        userService.deleteUserById(userId);
    }
}