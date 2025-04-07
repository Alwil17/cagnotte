package com.grey.cagnotte.seeder;

import com.grey.cagnotte.entity.Permission;
import com.grey.cagnotte.entity.User;
import com.grey.cagnotte.payload.request.PermissionRequest;
import com.grey.cagnotte.payload.request.UserRequest;
import com.grey.cagnotte.service.PermissionService;
import com.grey.cagnotte.service.RoleService;
import com.grey.cagnotte.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Log4j2
@RequiredArgsConstructor
public class DatabaseSeeder {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final UserService userService;
    private final RoleService roleService;
    private final PermissionService permissionService;

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedPermissionsTable();
        seedRoleTable();
        seedUserTable();
    }

    private void seedPermissionsTable() {
        String sql = "SELECT c.titre FROM `user` c";
        List<Permission> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(rs == null || rs.size() <= 0) {
            PermissionRequest ar1 = PermissionRequest.builder().titre("ADD_USER").build();
            PermissionRequest ar2 = PermissionRequest.builder().titre("DELETE_USER").build();
            PermissionRequest ar3 = PermissionRequest.builder().titre("ADD_ROLE").build();
            PermissionRequest ar4 = PermissionRequest.builder().titre("ADD_PERMISSION").build();
            PermissionRequest ar5 = PermissionRequest.builder().titre("DELETE_PERMISSION").build();
            PermissionRequest ar6 = PermissionRequest.builder().titre("DELETE_ROLE").build();
            PermissionRequest ar7 = PermissionRequest.builder().titre("ADD_CATEGORY").build();
            PermissionRequest ar8 = PermissionRequest.builder().titre("DELETE_CATEGORY").build();
            PermissionRequest ar9 = PermissionRequest.builder().titre("ADD_ETAT").build();
            PermissionRequest ar10 = PermissionRequest.builder().titre("DELETE_ETAT").build();

            permissionService.addPermissions(Arrays.asList(ar1,ar2,ar3,ar4,ar5,ar6,ar7,ar8,ar9,ar10));

            log.info("users table seeded");
        }else {
            log.info("User Seeding Not Required");
        }
    }
    private void seedRoleTable() {
        String sql = "SELECT c.nom FROM `user` c";
        List<User> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(rs == null || rs.size() <= 0) {
            UserRequest ar1 = UserRequest.builder()
                    .nom("Gringo")
                    .prenoms("Admin")
                    .username("gadmin")
                    .email("gadmin@cagnotte.com")
                    .tel1("98574896")
                    .password("azerty@123")
                    .type("admin")
                    .build();

            userService.addUser(Arrays.asList(ar1));

            log.info("users table seeded");
        }else {
            log.info("User Seeding Not Required");
        }
    }

    private void seedUserTable() {
        String sql = "SELECT c.nom FROM `user` c";
        List<User> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(rs == null || rs.size() <= 0) {
            UserRequest ar1 = UserRequest.builder()
                    .nom("Gringo")
                    .prenoms("Admin")
                    .username("gadmin")
                    .email("gadmin@cagnotte.com")
                    .tel1("98574896")
                    .password("azerty@123")
                    .type("admin")
                    .build();

            userService.addUser(Arrays.asList(ar1));

            log.info("users table seeded");
        }else {
            log.info("User Seeding Not Required");
        }
    }

}
