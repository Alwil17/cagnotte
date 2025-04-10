package com.grey.cagnotte.service.impl;

import com.grey.cagnotte.entity.Permission;
import com.grey.cagnotte.payload.request.PermissionRequest;
import com.grey.cagnotte.repository.PermissionRepository;
import com.grey.cagnotte.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public Permission createPermission(PermissionRequest permissionRequest) {
        if (permissionRepository.findByTitle(permissionRequest.getTitle()).isPresent()) {
            throw new IllegalArgumentException("Permission with this title already exists.");
        }

        Permission permission = Permission.builder()
                .title(permissionRequest.getTitle())
                .build();

        return permissionRepository.save(permission);
    }

    @Override
    public Permission getPermissionById(long id) {
        return permissionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Permission not found."));
    }

    @Override
    public void updatePermission(PermissionRequest permissionRequest, long id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Permission not found."));
        permission.setTitle(permissionRequest.getTitle());
        permissionRepository.save(permission);
    }

    @Override
    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    @Override
    public void addPermissions(List<PermissionRequest> permissionRequests) {
        if(permissionRequests != null && !permissionRequests.isEmpty()){
            for (PermissionRequest permissionRequest : permissionRequests) {
                if (permissionRepository.findByTitle(permissionRequest.getTitle()).isPresent()) {
                    throw new IllegalArgumentException("Permission with this title already exists.");
                }

                Permission permission = Permission.builder()
                        .title(permissionRequest.getTitle())
                        .build();

                permissionRepository.save(permission);
            }
        }
    }
}
