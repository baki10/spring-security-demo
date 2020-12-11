package com.bakigoal.keycloakdemo.rest;

import com.bakigoal.keycloakdemo.model.Workout;
import com.bakigoal.keycloakdemo.service.WorkoutService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workout")
@AllArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;

    @PostMapping("/")
    public void add(@RequestBody Workout workout) {
        workoutService.saveWorkout(workout);
    }

    @GetMapping("/")
    public List<Workout> workouts() {
        return workoutService.findWorkouts();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        workoutService.deleteWorkout(id);
    }

}
