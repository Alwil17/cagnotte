package com.grey.cagnotte.service.impl;

import com.grey.cagnotte.entity.Permission;
import com.grey.cagnotte.entity.Role;
import com.grey.cagnotte.exception.CagnotteCustomException;
import com.grey.cagnotte.payload.request.RoleRequest;
import com.grey.cagnotte.payload.response.RoleResponse;
import com.grey.cagnotte.repository.PermissionRepository;
import com.grey.cagnotte.repository.RoleRepository;
import com.grey.cagnotte.service.RoleService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class RoleServiceImpl implements RoleService {
    private final String NOT_FOUND = "ROLE_NOT_FOUND";
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public long addRole(RoleRequest roleRequest) {
        log.info("RoleServiceImpl | addRole is called");
        Role role
                = Role.builder()
                .name(roleRequest.getName())
                .build();

        //role = roleRepository.save(role);

        if(roleRequest.getPermissions() != null && !roleRequest.getPermissions().isEmpty()){
            List<Permission> permissions = new ArrayList<>();
            roleRequest.getPermissions().forEach(permissionResponse -> {
                // Traiter le cas où la permission n'est pas trouvée
                permissionRepository.findByTitle(permissionResponse.getTitle())
                        .ifPresentOrElse(
                                permissions::add, // Ajouter la permission si elle existe
                                () -> {
                                    throw new RuntimeException("Permission non trouvée: " + permissionResponse.getTitle());
                                }
                        );
            });
            if (!permissions.isEmpty()) {
                role.setPermissions(permissions);
                log.info(permissions.size());
            }
        }

        role = roleRepository.save(role);

        log.info("RoleServiceImpl | addRole | Role Created");
        log.info("RoleServiceImpl | addRole | Role Id : " + role.getId());
        return role.getId();
    }

    @Override
    public void addRole(List<RoleRequest> roleRequests) {
        log.info("RoleServiceImpl | addRole is called");

        for (RoleRequest roleRequest : roleRequests) {
            Role role
                    = Role.builder()
                    .name(roleRequest.getName())
                    .build();

            if(roleRequest.getPermissions() != null && !roleRequest.getPermissions().isEmpty()){
                List<Permission> permissions = new ArrayList<>();
                roleRequest.getPermissions().forEach(permissionResponse -> {
                    // Traiter le cas où la permission n'est pas trouvée
                    permissionRepository.findByTitle(permissionResponse.getTitle())
                            .ifPresentOrElse(
                                    permissions::add, // Ajouter la permission si elle existe
                                    () -> {
                                        throw new RuntimeException("Permission non trouvée: " + permissionResponse.getTitle());
                                    }
                            );
                });
                if (!permissions.isEmpty()) {
                    role.setPermissions(permissions);
                    log.info(permissions.size());
                }
            }

            roleRepository.save(role);
        }

        log.info("RoleServiceImpl | addRole | Roles Created");
    }

    @Override
    public RoleResponse getRoleById(long roleId) {
        log.info("RoleServiceImpl | getRoleById is called");
        log.info("RoleServiceImpl | getRoleById | Get the role for roleId: {}", roleId);

        Role role
                = roleRepository.findById(roleId)
                .orElseThrow(
                        () -> new CagnotteCustomException("Role with given Id not found", NOT_FOUND));

        return mapToResponse(role);
    }

    @Override
    public void editRole(RoleRequest roleRequest, long roleId) {
        log.info("RoleServiceImpl | editRole is called");

        Role role
                = roleRepository.findById(roleId)
                .orElseThrow(() -> new CagnotteCustomException(
                        "Role with given Id not found",
                        NOT_FOUND
                ));
        role.setName(roleRequest.getName());
        //roleRepository.save(role);

        if(roleRequest.getPermissions() != null && !roleRequest.getPermissions().isEmpty()){
            List<Permission> permissions = new ArrayList<>();
            roleRequest.getPermissions().forEach(permissionResponse -> {
                // Traiter le cas où la permission n'est pas trouvée
                permissionRepository.findByTitle(permissionResponse.getTitle())
                        .ifPresentOrElse(
                                permissions::add, // Ajouter la permission si elle existe
                                () -> {
                                    throw new RuntimeException("Permission non trouvée: " + permissionResponse.getTitle());
                                }
                        );
            });
            if (!permissions.isEmpty()) {
                role.setPermissions(permissions);
                log.info(permissions.size());
            }
        }

        role = roleRepository.save(role);

        log.info("RoleServiceImpl | editRole | Role Updated");
        log.info("RoleServiceImpl | editRole | Role Id : " + role.getId());
    }

    @Override
    public void deleteRoleById(long roleId) {
        log.info("Role id: {}", roleId);

        if (!roleRepository.existsById(roleId)) {
            log.info("Im in this loop {}", !roleRepository.existsById(roleId));
            throw new CagnotteCustomException(
                    "Role with given with Id: " + roleId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Role with id: {}", roleId);
        roleRepository.deleteById(roleId);
    }

    public RoleResponse mapToResponse(Role role){
        RoleResponse roleResponse = new RoleResponse();

        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new JavaTimeModule());
        roleResponse = mapper.convertValue(role, RoleResponse.class);

        return roleResponse;
    }
}
