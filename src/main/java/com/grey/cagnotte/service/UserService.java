package com.grey.cagnotte.service;

import com.grey.cagnotte.entity.User;
import com.grey.cagnotte.payload.request.UserRequest;
import com.grey.cagnotte.payload.response.UserResponse;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    long addUser(UserRequest userRequest);

    void addUser(List<UserRequest> userRequests);

    UserResponse getUserByEmail(String userEmail);

    UserResponse getUserById(long userId);

    UserResponse getUserByUsername(String username);

    void editUser(UserRequest userRequest, long userId);

    public void deleteUserById(long userId);
}
