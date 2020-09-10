package com.bakigoal.spring.controller;

import com.bakigoal.spring.security.Auth;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
public class MyController {

    @GetMapping("/")
    public String hello() {
        logUser();
        return "Hello world!";
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
