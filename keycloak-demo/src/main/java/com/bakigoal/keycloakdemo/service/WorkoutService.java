package com.bakigoal.keycloakdemo.service;

import com.bakigoal.keycloakdemo.model.Workout;
import com.bakigoal.keycloakdemo.repository.WorkoutRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class WorkoutService {

    private final WorkoutRepository workoutRepository;

    /**
     * By preAuthorization, ensures the
     * method isn’t called if the workout
     * record doesn’t belong to the user
     */
    @PreAuthorize("#workout.username == authentication.name")
    public void saveWorkout(Workout workout){
        workoutRepository.save(workout);
    }

    /**
     * For this method, we already applied
     * filtering at the repository layer.
     */
    public List<Workout> finWorkouts() {
        return workoutRepository.findAllByUser();
    }

    /**
     * Applies authorization for this
     * method at the endpoint layer
     */
    public void deleteWorkout(Integer id) {
        workoutRepository.deleteById(id);
    }
}
