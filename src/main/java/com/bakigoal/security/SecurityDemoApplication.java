package com.bakigoal.security;

import com.bakigoal.security.domain.MyAuthority;
import com.bakigoal.security.domain.MyUser;
import com.bakigoal.security.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableWebSecurity
@Slf4j
public class SecurityDemoApplication {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public SecurityDemoApplication(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(SecurityDemoApplication.class, args);
    }

    @PostConstruct
    public void init() {
        // test users
        userRepo.save(MyUser.create("admin", passwordEncoder.encode("password"), MyAuthority.ROLE_ADMIN));
        userRepo.save(MyUser.create("user", passwordEncoder.encode("password"), MyAuthority.ROLE_USER));

        log.info("" + userRepo.findAll());
    }

}
