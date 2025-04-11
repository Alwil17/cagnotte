package com.grey.cagnotte.payload.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class LoginRequest {
    private String username;
    private String password;
}