package com.bakigoal.spring.util;

import com.bakigoal.spring.domain.Role;
import com.bakigoal.spring.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Slf4j
@AllArgsConstructor
public class TestDataGenerator {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        createUser("admin", Role.ADMIN, Role.USER);
        createUser("user", Role.USER);
        createUser("hr", Role.HR);
        createUser("dev", Role.DEV, Role.USER);

        log.info("!!! USERS:" + userService.findAll());
    }

    private void createUser(String name, Role... roles) {
        userService.createUserWithRoles(name, passwordEncoder.encode("password"), roles);
    }
}
