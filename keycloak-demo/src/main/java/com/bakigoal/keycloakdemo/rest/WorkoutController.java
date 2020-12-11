package com.bakigoal.keycloakdemo.rest;

import com.bakigoal.keycloakdemo.model.Workout;
import com.bakigoal.keycloakdemo.service.WorkoutService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/workout")
@AllArgsConstructor
@Slf4j
public class WorkoutController {

    private final WorkoutService workoutService;

    @PostMapping("/")
    public void add(@RequestBody Workout workout) {
        workoutService.saveWorkout(workout);
    }

    @GetMapping("/")
    public List<Workout> workouts(OAuth2Authentication authentication) {
        logUser(authentication);
        return workoutService.findWorkouts();
    }

    private void logUser(OAuth2Authentication authentication) {
        Collection<GrantedAuthority> authorities = authentication.getAuthorities();
        String name = authentication.getName();

        log.info("User: {}, Roles: {}", name, authorities);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        workoutService.deleteWorkout(id);
    }

}
