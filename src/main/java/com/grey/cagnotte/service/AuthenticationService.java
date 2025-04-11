package com.grey.cagnotte.service;

import com.grey.cagnotte.entity.User;
import com.grey.cagnotte.payload.request.RegisterRequest;

public interface AuthenticationService {
    String login(String username, String password);
    User register(RegisterRequest user);
}
