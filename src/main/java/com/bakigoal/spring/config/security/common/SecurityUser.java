package com.bakigoal.spring.config.security.common;

import com.bakigoal.spring.domain.DbRole;
import com.bakigoal.spring.domain.Role;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class SecurityUser extends User {

    private final List<Role> roles;

    public SecurityUser(String username, String password, List<DbRole> dbRoles) {
        super(username, password, dbRoles.stream().map(DbRole::getRole)
                .map(r -> new SimpleGrantedAuthority(r.name())).collect(Collectors.toList()));
        this.roles = dbRoles.stream().map(DbRole::getRole).collect(Collectors.toList());
    }
}
