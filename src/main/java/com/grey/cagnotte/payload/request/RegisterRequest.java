package com.grey.cagnotte.payload.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class RegisterRequest {
    private String username;
    private String lastname;
    private String firstname;
    private String email;
    private String tel1;
    private String tel2;
    private String address;
    private String password;
}