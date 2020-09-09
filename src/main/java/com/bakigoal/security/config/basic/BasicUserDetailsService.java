package com.bakigoal.security.config.basic;

import com.bakigoal.security.domain.MyUser;
import com.bakigoal.security.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;

@Slf4j
public class BasicUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    public BasicUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser myUser = userRepo.findByName(username);
        if (myUser == null) {
            log.info("User " + username + " was not found in the database");
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }

        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(myUser.getMyAuthority().name()));

        return new User(username, myUser.getPassword(), authorities);
    }
}
