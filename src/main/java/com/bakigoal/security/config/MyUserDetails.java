package com.bakigoal.security.config;

import com.bakigoal.security.domain.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

public class MyUserDetails extends User {

    private final Role role;

    public MyUserDetails(String username, String password, Role role) {
        super(username, password, Collections.singletonList(new SimpleGrantedAuthority(role.name())));
        this.role = role;
    }

    public Role getRole() {
        return role;
    }
}
