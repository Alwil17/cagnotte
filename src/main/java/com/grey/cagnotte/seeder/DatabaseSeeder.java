package com.grey.cagnotte.seeder;

import com.grey.cagnotte.entity.User;
import com.grey.cagnotte.payload.request.UserRequest;
import com.grey.cagnotte.service.UserService;
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
public class DatabaseSeeder {
    private JdbcTemplate jdbcTemplate;

    private final UserService userService;

    @Autowired
    public DatabaseSeeder(JdbcTemplate jdbcTemplate, UserService userService) {
        this.jdbcTemplate = jdbcTemplate;
        this.userService = userService;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedUserTable();
    }

    private void seedUserTable() {
        String sql = "SELECT c.nom FROM `user` c";
        List<User> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(rs == null || rs.size() <= 0) {
            UserRequest ar1 = UserRequest.builder()
                    .nom("Gringo")
                    .prenoms("Admin")
                    .email("gadmin@cagnotte.com")
                    .tel1("98574896")
                    .password("azerty123")
                    .type("admin")
                    .build();

            userService.addUser(Arrays.asList(ar1));

            log.info("users table seeded");
        }else {
            log.info("User Seeding Not Required");
        }
    }

}
