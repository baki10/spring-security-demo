package com.bakigoal.keycloakdemo.repository;

import com.bakigoal.keycloakdemo.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, Integer> {

    @Query("SELECT w FROM Workout w WHERE w.username = ?#{authentication.name}")
    List<Workout> findAllByUser();
}
