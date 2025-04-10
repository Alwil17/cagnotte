package com.grey.cagnotte.service;

import com.grey.cagnotte.entity.Role;
import com.grey.cagnotte.payload.request.RoleRequest;
import com.grey.cagnotte.payload.response.RoleResponse;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();

    Role addRole(RoleRequest roleRequest);

    void addRole(List<RoleRequest> roleRequests);

    RoleResponse getRoleById(long roleId);

    void editRole(RoleRequest roleRequest, long roleId);

    public void deleteRoleById(long roleId);
}
