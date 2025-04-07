package com.grey.cagnotte.controller;

import com.grey.cagnotte.entity.Permission;
import com.grey.cagnotte.payload.response.PermissionResponse;
import com.grey.cagnotte.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @PostMapping
    public ResponseEntity<Permission> createPermission(@RequestBody PermissionResponse permissionResponse) {
        return ResponseEntity.ok(permissionService.createPermission(permissionResponse));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Permission> getPermissionById(@PathVariable long id) {
        return ResponseEntity.ok(permissionService.getPermissionById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePermission(@PathVariable long id, @RequestBody PermissionResponse permissionResponse) {
        permissionService.updatePermission(permissionResponse, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Permission>> getAllPermissions() {
        return ResponseEntity.ok(permissionService.getAllPermissions());
    }
}
