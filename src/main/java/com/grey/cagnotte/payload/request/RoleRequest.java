package com.grey.cagnotte.payload.request;

import com.grey.cagnotte.payload.response.PermissionResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleRequest {
    private String name;

    private List<PermissionResponse> permissions;
}
