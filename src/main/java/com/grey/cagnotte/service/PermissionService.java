package com.grey.cagnotte.service;

import com.grey.cagnotte.entity.Permission;
import com.grey.cagnotte.payload.request.PermissionRequest;

import java.util.List;

public interface PermissionService {
    Permission createPermission(PermissionRequest permissionRequest);
    Permission getPermissionById(long id);
    void updatePermission(PermissionRequest permissionRequest, long id);
    List<Permission> getAllPermissions();

    void addPermissions(List<PermissionRequest> permissionRequests);
}
