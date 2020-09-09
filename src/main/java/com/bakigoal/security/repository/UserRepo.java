package com.bakigoal.security.repository;

import com.bakigoal.security.domain.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepo extends JpaRepository<MyUser, UUID> {

    MyUser findByName(String name);
}
