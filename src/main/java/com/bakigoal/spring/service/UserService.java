package com.bakigoal.spring.service;

import com.bakigoal.spring.domain.DbRole;
import com.bakigoal.spring.domain.MyUser;
import com.bakigoal.spring.domain.Role;
import com.bakigoal.spring.repository.DbRoleRepo;
import com.bakigoal.spring.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final DbRoleRepo dbRoleRepo;

    public List<MyUser> findAll() {
        return userRepo.findAll();
    }

    public void saveUser(MyUser user) {
        userRepo.save(user);
    }

    public void saveRole(DbRole dbRole) {
        dbRoleRepo.save(dbRole);
    }


    public void createUserWithRoles(String name, String password, Role... roles) {
        MyUser user = new MyUser();
        user.setName(name);
        user.setPassword(password);

        List<DbRole> dbRoles = new ArrayList<>();
        for (Role role : roles) {
            DbRole dbRole = new DbRole();
            dbRole.setRole(role);
            dbRoleRepo.save(dbRole);
            dbRoles.add(dbRole);
        }
        user.setRoles(dbRoles);
        userRepo.save(user);
    }
}
