package com.bakigoal.spring.service;

import com.bakigoal.spring.domain.Role;
import com.bakigoal.spring.security.annotation.Allow;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class RolesService {

    @PreAuthorize("hasRole('ADMIN')")
    public String getAdminData() {
        return "Admin data";
    }

    @Secured({"ROLE_USER"})
    public String getUserData() {
        return "User data";
    }

    @Allow(roles = {Role.ADMIN, Role.USER})
    public String getUserOrAdminData() {
        return "User and Admin can see this data";
    }
}
