package com.bakigoal.spring.config.security.common;

import com.bakigoal.spring.domain.MyUser;
import com.bakigoal.spring.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    public MyUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser myUser = userRepo.findByName(username);
        if (myUser == null) {
            log.info("User " + username + " was not found in the database");
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }

        return new MyUserDetails(username, myUser.getPassword(), myUser.getRoles());
    }
}
