package com.bakigoal.security.service;

import com.bakigoal.security.domain.Role;
import com.bakigoal.security.util.security.annotation.Allow;
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

    @Allow(roles = {Role.ROLE_ADMIN, Role.ROLE_USER})
    public String getUserOrAdminData() {
        return "User and Admin can see this data";
    }
}
