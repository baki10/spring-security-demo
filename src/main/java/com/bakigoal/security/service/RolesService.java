package com.bakigoal.security.service;

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
}
