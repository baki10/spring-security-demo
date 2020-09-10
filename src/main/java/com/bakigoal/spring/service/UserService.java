package com.bakigoal.spring.service;

import com.bakigoal.spring.domain.DbRole;
import com.bakigoal.spring.domain.MyUser;
import com.bakigoal.spring.domain.Role;
import com.bakigoal.spring.repository.DbRoleRepo;
import com.bakigoal.spring.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final DbRoleRepo dbRoleRepo;

    public List<MyUser> findAll() {
        return userRepo.findAll();
    }

    public void createUserWithRoles(String name, String password, Role... roles) {
        MyUser user = new MyUser();
        user.setName(name);
        user.setPassword(password);
        user.setRoles(
                Arrays.stream(roles).map(this::saveRole).collect(Collectors.toList())
        );
        userRepo.save(user);
    }

    private DbRole saveRole(Role role) {
        var dbRole = new DbRole();
        dbRole.setRole(role);
        return dbRoleRepo.save(dbRole);
    }
}
