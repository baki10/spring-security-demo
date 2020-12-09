package com.bakigoal.spring.config.security.jwt.provider;

import com.bakigoal.spring.config.security.jwt.exception.GettingUserFromTokenException;
import com.bakigoal.spring.config.security.jwt.util.JwtTokenUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "app.security.type", havingValue = "jwt")
@AllArgsConstructor
@Slf4j
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthentication jwtAuthentication = (JwtAuthentication) authentication;
        String token = jwtAuthentication.getToken();
        String username = extractUsernameFromToken(token);

        UserDetails user = this.userDetailsService.loadUserByUsername(username);
        if (jwtTokenUtil.tokenIsValid(token, user)) {
            return new JwtAuthentication(user, null, user.getAuthorities());
        }
        throw new BadCredentialsException("Wrong token");
    }

    @Override
    public boolean supports(Class<?> authenticationType) {
        return JwtAuthentication.class.isAssignableFrom(authenticationType);
    }

    private String extractUsernameFromToken(String token) {
        try {
            return jwtTokenUtil.getUsernameFromToken(token);
        } catch (Exception e) {
            throw new GettingUserFromTokenException("Could not get username form token");
        }
    }
}
