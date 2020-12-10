package com.bakigoal.spring.config.security.common;

import com.bakigoal.spring.domain.Role;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.stream.Collectors;

public class SecurityUser extends User {

    public SecurityUser(String username, String password, List<SecurityAuthority> authorities) {
        super(username, password, authorities);
    }

    public List<Role> getRoles() {
        return super.getAuthorities().stream()
                .map(grantedAuthority -> (SecurityAuthority) grantedAuthority)
                .map(SecurityAuthority::getRole)
                .collect(Collectors.toList());
    }
}
