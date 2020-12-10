package com.bakigoal.spring.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class MyUser {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<DbRole> roles = new ArrayList<>();

}
