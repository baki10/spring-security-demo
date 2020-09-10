package com.bakigoal.security.controller;

import com.bakigoal.security.service.RolesService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
@AllArgsConstructor
public class RolesController {

    private final RolesService rolesService;

    @GetMapping("/admin")
    public String admin() {
        return rolesService.getAdminData();
    }

    @GetMapping("/user")
    public String user() {
        return rolesService.getUserData();
    }

    @GetMapping("/userOrAdmin")
    public String userAdmin() {
        return rolesService.getUserOrAdminData();
    }
}
