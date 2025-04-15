package com.grey.cagnotte.service;

import com.grey.cagnotte.entity.User;
import com.grey.cagnotte.payload.request.UserRequest;
import com.grey.cagnotte.payload.response.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllUsers();

    UserResponse addUser(UserRequest userRequest);

    void addUser(List<UserRequest> userRequests);

    UserResponse getUserByEmail(String userEmail);

    UserResponse getUserById(long userId);

    User getMe();

    UserResponse getUserByUsername(String username);

    UserResponse editUser(UserRequest userRequest, long userId);

    public void deleteUserById(long userId);
}
