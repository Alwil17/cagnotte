package com.grey.cagnotte.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private long id;
    private String username;
    private String nom;
    private String prenoms;
    private String email;
    private String tel1;
    private String tel2;
    private String adresse;
    private String type;
    @JsonProperty("is_active")
    private boolean is_active;
    private LocalDateTime last_access_time;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    private List<RoleResponse> roles;
    private List<PermissionResponse> permissions;
}
