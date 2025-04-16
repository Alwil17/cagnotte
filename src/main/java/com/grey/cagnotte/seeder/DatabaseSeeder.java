package com.grey.cagnotte.seeder;

import com.grey.cagnotte.enums.RoleEnum;
import com.grey.cagnotte.entity.Permission;
import com.grey.cagnotte.entity.Role;
import com.grey.cagnotte.entity.State;
import com.grey.cagnotte.entity.User;
import com.grey.cagnotte.enums.StateEnum;
import com.grey.cagnotte.payload.request.*;
import com.grey.cagnotte.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
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
    private final StateService stateService;
    private final CategoryService categoryService;

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedPermissionsTable();
        seedRoleTable();
        seedUserTable();
        seedStateTable();
        seedCategoryTable();
    }

    private void seedPermissionsTable() {
        String sql = "SELECT c.title FROM permissions c";
        List<Permission> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if (rs == null || rs.size() <= 0) {
            PermissionRequest ar1 = PermissionRequest.builder().title("ADD_USER").build();
            PermissionRequest ar2 = PermissionRequest.builder().title("DELETE_USER").build();
            PermissionRequest ar3 = PermissionRequest.builder().title("ADD_ROLE").build();
            PermissionRequest ar4 = PermissionRequest.builder().title("ADD_PERMISSION").build();
            PermissionRequest ar5 = PermissionRequest.builder().title("DELETE_PERMISSION").build();
            PermissionRequest ar6 = PermissionRequest.builder().title("DELETE_ROLE").build();
            PermissionRequest ar7 = PermissionRequest.builder().title("ADD_CATEGORY").build();
            PermissionRequest ar8 = PermissionRequest.builder().title("DELETE_CATEGORY").build();
            PermissionRequest ar9 = PermissionRequest.builder().title("ADD_STATE").build();
            PermissionRequest ar10 = PermissionRequest.builder().title("DELETE_STATE").build();

            permissionService.addPermissions(Arrays.asList(ar1, ar2, ar3, ar4, ar5, ar6, ar7, ar8, ar9, ar10));

            log.info("Permission table seeded");
        } else {
            log.info("Permission Seeding Not Required");
        }
    }

    private void seedRoleTable() {
        String sql = "SELECT c.name FROM roles c";
        List<Role> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if (rs == null || rs.size() <= 0) {
            RoleRequest ar1 = RoleRequest.builder().name(RoleEnum.ADMIN.name()).build();
            RoleRequest ar2 = RoleRequest.builder().name(RoleEnum.SUPER_ADMIN.name()).build();
            RoleRequest ar3 = RoleRequest.builder().name(RoleEnum.USER.name()).build();

            roleService.addRole(Arrays.asList(ar1, ar2, ar3));

            log.info("Role table seeded");
        } else {
            log.info("Role Seeding Not Required");
        }
    }

    private void seedUserTable() {
        String sql = "SELECT c.email FROM users c";
        List<User> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if (rs == null || rs.size() <= 0) {
            UserRequest ar1 = UserRequest.builder()
                    .lastname("Gringo")
                    .firstname("Admin")
                    .username("gadmin")
                    .email("gadmin@cagnotte.com")
                    .tel1("98574896")
                    .password("azerty@123")
                    .is_active(true)
                    .roles(Collections.singletonList(RoleRequest.builder().name(RoleEnum.SUPER_ADMIN.name()).build()))
                    .build();

            userService.addUser(ar1);

            log.info("users table seeded");
        } else {
            log.info("User Seeding Not Required");
        }
    }

    private void seedStateTable() {
        String sql = "SELECT COUNT(*) FROM states";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        if (count == null || count <= 0) {
            List<StateRequest> states = List.of(
                    StateRequest.builder()
                            .label(StateEnum.DRAFT.name())
                            .description("CagnotteApplication not yet published")
                            .color("#FFC107") // Yellow
                            .build(),
                    StateRequest.builder()
                            .label(StateEnum.ACTIVE.name())
                            .description("CagnotteApplication accepting contributions")
                            .color("#28A745") // Green
                            .build(),
                    StateRequest.builder()
                            .label(StateEnum.CLOSED.name())
                            .description("CagnotteApplication manually closed")
                            .color("#DC3545") // Red
                            .build(),
                    StateRequest.builder()
                            .label(StateEnum.EXPIRED.name())
                            .description("CagnotteApplication has reached its deadline")
                            .color("#6C757D") // Gray
                            .build(),
                    StateRequest.builder()
                            .label(StateEnum.ARCHIVED.name())
                            .description("CagnotteApplication archived for record")
                            .color("#007BFF") // Blue
                            .build(),
                    StateRequest.builder()
                            .label(StateEnum.CANCELED.name())
                            .description("CagnotteApplication was canceled")
                            .color("#FD7E14") // Orange
                            .build(),
                    StateRequest.builder()
                            .label(StateEnum.UNDER_REVIEW.name())
                            .description("Pending verification or review")
                            .color("#6F42C1") // Purple
                            .build()
            );

            states.forEach(stateService::addState);

            log.info("State table seeded");
        } else {
            log.info("State Seeding Not Required");
        }
    }

    private void seedCategoryTable() {
        String sql = "SELECT COUNT(*) FROM category";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);

        if (count == null || count <= 0) {
            List<CategoryRequest> categories = List.of(
                    new CategoryRequest("FAMILY", "Family-related occasions", "👨‍👩‍👧‍👦"),
                    new CategoryRequest("WEDDING", "Marriage and engagement support", "💍"),
                    new CategoryRequest("BIRTHDAY", "Birthday gifts and surprises", "🎂"),
                    new CategoryRequest("EDUCATION", "School and tuition support", "🎓"),
                    new CategoryRequest("HEALTH", "Medical and emergency funds", "🏥"),
                    new CategoryRequest("FUNERAL", "Funeral and memorial support", "⚰️"),
                    new CategoryRequest("PROJECT", "Creative or business projects", "💡"),
                    new CategoryRequest("SPORT", "Athletic or team fundraising", "⚽"),
                    new CategoryRequest("TRAVEL", "Trips or honeymoon support", "✈️"),
                    new CategoryRequest("CHARITY", "Charitable donations and causes", "❤️"),
                    new CategoryRequest("OTHER", "Miscellaneous or custom cagnottes", "🔖")
            );

            categories.forEach(categoryService::addCategory);

            log.info("Categories table seeded successfully");
        } else {
            log.info("Categories already exist, skipping seeding.");
        }
    }

}
