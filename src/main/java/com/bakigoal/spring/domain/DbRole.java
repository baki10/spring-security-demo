package com.bakigoal.spring.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class DbRole {

    @Id
    @GeneratedValue
    private int id;
    @Enumerated
    private Role role;
}
