package com.bakigoal.spring.repository;

import com.bakigoal.spring.domain.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<MyUser, Integer> {

    MyUser findByName(String name);
}
