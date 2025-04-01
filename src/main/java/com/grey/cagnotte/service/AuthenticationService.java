package com.grey.cagnotte.service;

import com.grey.cagnotte.entity.User;

public interface AuthenticationService {
    String login(String username, String password);
    User register(User user);
}
