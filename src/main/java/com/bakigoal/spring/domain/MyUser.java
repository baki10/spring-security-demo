package com.bakigoal.spring.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Data
@Entity
public class MyUser {

    @Id
    private UUID id;
    private String name;
    private String password;
    private Role role;

    public static MyUser create(String name, String password, Role role) {
        MyUser myUser = new MyUser();
        myUser.setId(UUID.randomUUID());
        myUser.setName(name);
        myUser.setPassword(password);
        myUser.setRole(role);
        return myUser;
    }
}
