package com.bakigoal.spring.controller;

import com.bakigoal.spring.config.security.common.MyUserAuthenticationProvider;
import com.bakigoal.spring.config.security.jwt.JwtRequest;
import com.bakigoal.spring.config.security.jwt.JwtResponse;
import com.bakigoal.spring.config.security.jwt.JwtTokenUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class AuthController {

    public static final String JWT_AUTH_URL = "/jwt/authenticate";

    private final MyUserAuthenticationProvider authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;

    @PostMapping(JWT_AUTH_URL)
    public ResponseEntity<JwtResponse> getToken(@RequestBody JwtRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        log.info("Generated token for user {}: {}", authRequest.getUsername(), token);
        return ResponseEntity.ok(new JwtResponse(token));
    }

}
