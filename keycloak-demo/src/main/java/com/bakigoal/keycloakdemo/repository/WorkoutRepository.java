package com.bakigoal.keycloakdemo.repository;

import com.bakigoal.keycloakdemo.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, Integer> {

    List<Workout> findAllByUsername(String username);
}
