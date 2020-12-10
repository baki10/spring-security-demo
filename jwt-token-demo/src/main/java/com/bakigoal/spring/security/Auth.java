package com.bakigoal.spring.security;

import com.bakigoal.spring.config.security.common.SecurityUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class Auth {

    public static Optional<SecurityUser> getCurrentUser() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .filter(user -> user instanceof SecurityUser)
                .map(o -> (SecurityUser) o);
    }
}
