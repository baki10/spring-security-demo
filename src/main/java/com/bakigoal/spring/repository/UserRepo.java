package com.bakigoal.spring.repository;

import com.bakigoal.spring.domain.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepo extends JpaRepository<MyUser, UUID> {

    MyUser findByName(String name);
}
