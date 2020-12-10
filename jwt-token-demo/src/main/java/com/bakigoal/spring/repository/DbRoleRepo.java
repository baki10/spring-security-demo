package com.bakigoal.spring.repository;

import com.bakigoal.spring.domain.DbRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DbRoleRepo extends JpaRepository<DbRole, Integer> {
}
