package com.grey.cagnotte.payload.request;

import com.grey.cagnotte.payload.response.PermissionResponse;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class UserRequest {
    private String username;
    private String nom;
    private String prenoms;
    private String email;
    private String tel1;
    private String tel2;
    private String adresse;
    private String password;
    private String type;

    private boolean is_active;
    private LocalDateTime last_access_time;

    List<RoleRequest> roles;
    List<PermissionRequest> permissions;
}