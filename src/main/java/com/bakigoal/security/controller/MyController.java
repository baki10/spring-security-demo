package com.bakigoal.security.controller;

import com.bakigoal.security.config.MyUserDetails;
import com.bakigoal.security.config.jwt.JwtTokenUtil;
import com.bakigoal.security.util.security.Auth;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@Slf4j
@AllArgsConstructor
public class MyController {

    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String hello() {
        logUser();
        return "Hello world!";
    }

    @GetMapping("/token")
    public String pub(@RequestParam("name") String name,
                      @RequestParam("password") String password,
                      @RequestParam("role") String role,
                      @RequestParam("secret") String secret) {
        logUser();
        return jwtTokenUtil.generateToken(
                new User(name, passwordEncoder.encode(password), Collections.singletonList(new SimpleGrantedAuthority(role))),
                secret
        );
    }

    @GetMapping("/user")
    public String securedUser() {
        logUser();
        return "This Url is Secured!";
    }

    @GetMapping("/admin")
    public String securedAdmin() {
        logUser();
        return "This is ADMIN Page!";
    }

    private void logUser() {
        Auth.getCurrentUser().ifPresentOrElse(
                user -> log.info("User: {}, {}", user.getUsername(), user.getAuthorities()),
                () -> log.error("No user")
        );
    }
}
