package com.bakigoal.security.controller;

import com.bakigoal.security.config.jwt.JwtTokenUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Optional;

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
        Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .ifPresentOrElse(obj -> {
                    if (obj instanceof User) {
                        User user = (User) obj;
                        log.info("User: {}, {}", user.getUsername(), user.getAuthorities());
                    } else {
                        log.info("User is: {}", obj.toString());
                    }
                }, () -> log.error("No user"));
    }
}
