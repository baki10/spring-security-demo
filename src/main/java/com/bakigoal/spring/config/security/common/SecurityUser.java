package com.bakigoal.spring.config.security.common;

import com.bakigoal.spring.domain.Role;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public class SecurityUser extends User {

    private final List<Role> roles;

    public SecurityUser(String username, String password, List<Role> roles, List<SimpleGrantedAuthority> authorities) {
        super(username, password, authorities);
        this.roles = roles;
    }
}
