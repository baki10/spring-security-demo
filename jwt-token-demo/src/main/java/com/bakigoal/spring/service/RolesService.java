package com.bakigoal.spring.service;

import com.bakigoal.spring.domain.Role;
import com.bakigoal.spring.security.annotation.Allow;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;

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

    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public String rolesAllowed() {
        return "User and Admin can see this data";
    }

    @Allow(roles = {Role.ADMIN, Role.USER})
    public String getUserOrAdminData() {
        return "User and Admin can see this data";
    }

    @PreAuthorize("#name == authentication.principal.username")
    public String getSecretNames(String name) {
        return "Secret name: " + name;
    }

    /**
     * @param products The list given as param allows only products owned by the authenticated user
     */
    @PreFilter("filterObject.owner == authentication.name")
    public List<Product> sellProducts(List<Product> products) {
        // sell products and return the sold product list
        return products;
    }

    /**
     * Adds the filtering for the objects in the collection returned by the method
     */
    @PostFilter("filterObject.owner == authentication.name")
    public List<Product> findProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product().setOwner("baki"));
        products.add(new Product().setOwner("aliya"));
        products.add(new Product().setOwner("baki"));
        products.add(new Product().setOwner("zombi"));
        return products;
    }

    private static class Product {
        private String owner;

        public String getOwner() {
            return owner;
        }

        public Product setOwner(String owner) {
            this.owner = owner;
            return this;
        }
    }
}
