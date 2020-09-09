package com.bakigoal.security.domain;

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
    private MyAuthority myAuthority;

    public static MyUser create(String name, String password, MyAuthority myAuthority) {
        MyUser myUser = new MyUser();
        myUser.setId(UUID.randomUUID());
        myUser.setName(name);
        myUser.setPassword(password);
        myUser.setMyAuthority(myAuthority);
        return myUser;
    }
}
