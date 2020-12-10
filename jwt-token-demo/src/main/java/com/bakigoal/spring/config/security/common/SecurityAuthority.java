package com.bakigoal.spring.config.security.common;

import com.bakigoal.spring.domain.Role;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@EqualsAndHashCode
@Getter
public class SecurityAuthority implements GrantedAuthority {
    private static final String ROLE_ = "ROLE_";

    private final String authority;
    private final Role role;

    public SecurityAuthority(Role role) {
        this.role = role;
        this.authority = ROLE_ + role.name();
    }
}
