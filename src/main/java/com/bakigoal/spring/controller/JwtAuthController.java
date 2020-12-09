package com.bakigoal.spring.controller;

import com.bakigoal.spring.config.security.common.MyUserAuthenticationProvider;
import com.bakigoal.spring.config.security.jwt.dto.JwtRequest;
import com.bakigoal.spring.config.security.jwt.dto.JwtResponse;
import com.bakigoal.spring.config.security.jwt.util.JwtTokenUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ConditionalOnProperty(name = "app.security.type", havingValue = "jwt")
@AllArgsConstructor
@Slf4j
public class JwtAuthController {

    public static final String JWT_AUTH_URL = "/jwt/authenticate";

    private final MyUserAuthenticationProvider authenticationProvider;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;

    @PostMapping(JWT_AUTH_URL)
    public ResponseEntity<JwtResponse> getToken(@RequestBody JwtRequest authRequest) {
        authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        log.info("Generated token for user {}: {}", authRequest.getUsername(), token);
        return ResponseEntity.ok(new JwtResponse(token));
    }

}
