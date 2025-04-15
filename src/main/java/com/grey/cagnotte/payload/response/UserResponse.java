package com.grey.cagnotte.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private long id;
    private String username;
    private String lastname;
    private String firstname;
    private String email;
    private String tel1;
    private String tel2;
    private String address;
    private boolean isActive;
    private LocalDateTime last_access_time;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    private List<RoleResponse> roles;
    private List<PermissionResponse> permissions;
    private List authorities;
}
