package com.bakigoal.spring.config.security.common;

import com.bakigoal.spring.domain.DbRole;
import com.bakigoal.spring.domain.Role;
import com.bakigoal.spring.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
public class MyUserDetailsService implements UserDetailsService {

    private static final String ROLE_PREFIX = "ROLE_";

    private final UserRepo userRepo;

    public MyUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepo.findByName(username);
        if (user == null) {
            log.info("User " + username + " was not found in the database");
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }

        List<Role> roles = user.getRoles().stream().map(DbRole::getRole).collect(toList());
        List<GrantedAuthority> authorities = roles.stream().map(this::toAuthority).collect(toList());
        return new SecurityUser(username, user.getPassword(), roles, authorities);
    }

    public GrantedAuthority toAuthority(Role role) {
        return new SimpleGrantedAuthority(ROLE_PREFIX + role.name());
    }
}
