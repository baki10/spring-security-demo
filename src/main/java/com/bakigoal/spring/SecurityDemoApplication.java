package com.bakigoal.spring;

import com.bakigoal.spring.domain.Role;
import com.bakigoal.spring.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Slf4j
public class SecurityDemoApplication {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public SecurityDemoApplication(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(SecurityDemoApplication.class, args);
    }

    @PostConstruct
    public void init() {
        // test users
        create("admin", Role.ROLE_ADMIN, Role.ROLE_USER);
        create("user", Role.ROLE_USER);
        create("hr", Role.ROLE_HR);
        create("dev", Role.ROLE_DEV, Role.ROLE_USER);

        log.info("" + userService.findAll());
    }

    private void create(String name, Role... roles) {
        userService.createUserWithRoles(name, passwordEncoder.encode("password"), roles);
    }

}
