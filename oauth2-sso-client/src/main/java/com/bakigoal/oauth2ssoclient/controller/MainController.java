package com.bakigoal.oauth2ssoclient.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class MainController {

    @GetMapping("/")
    public String main() {
        return "main.html";
    }

    @GetMapping("/user")
    @ResponseBody
    public DefaultOAuth2User defaultOAuth2User(OAuth2AuthenticationToken token) {
        return (DefaultOAuth2User) token.getPrincipal();
    }

    @GetMapping("/test")
    @ResponseBody
    public String testThisMethod(Authentication authentication) {
        log.info("User: {}", authentication);
        return "User: " + authentication.getName() + ", Roles: " + authentication.getAuthorities();
    }
}
