package com.grey.cagnotte.controller;

import com.grey.cagnotte.entity.Role;
import com.grey.cagnotte.payload.request.RoleRequest;
import com.grey.cagnotte.payload.response.RoleResponse;
import com.grey.cagnotte.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@Log4j2
public class RoleController {
    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {

        log.info("RoleController | getAllRoles is called");
        return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addRole(@RequestBody RoleRequest roleRequest) {

        log.info("RoleController | addRole is called");

        log.info("RoleController | addRole | roleRequest : " + roleRequest.toString());

        long roleId = roleService.addRole(roleRequest);
        return new ResponseEntity<>(roleId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> getRoleById(@PathVariable("id") long roleId) {

        log.info("RoleController | getRoleById is called");

        log.info("RoleController | getRoleById | roleId : " + roleId);

        RoleResponse roleResponse
                = roleService.getRoleById(roleId);
        return new ResponseEntity<>(roleResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editRole(@RequestBody RoleRequest roleRequest,
                                         @PathVariable("id") long roleId
    ) {

        log.info("RoleController | editRole is called");

        log.info("RoleController | editRole | roleId : " + roleId);

        roleService.editRole(roleRequest, roleId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteRoleById(@PathVariable("id") long roleId) {
        roleService.deleteRoleById(roleId);
    }
}
