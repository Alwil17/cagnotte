package com.grey.cagnotte.controller;

import com.grey.cagnotte.entity.Permission;
import com.grey.cagnotte.payload.request.PermissionRequest;
import com.grey.cagnotte.payload.response.PermissionResponse;
import com.grey.cagnotte.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @PreAuthorize("hasAuthority('ADD_PERMISSION')")
    @PostMapping
    public ResponseEntity<Permission> createPermission(@RequestBody PermissionRequest permissionRequest) {
        return ResponseEntity.ok(permissionService.createPermission(permissionRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Permission> getPermissionById(@PathVariable long id) {
        return ResponseEntity.ok(permissionService.getPermissionById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePermission(@PathVariable long id, @RequestBody PermissionRequest permissionRequest) {
        permissionService.updatePermission(permissionRequest, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Permission>> getAllPermissions() {
        return ResponseEntity.ok(permissionService.getAllPermissions());
    }
}
