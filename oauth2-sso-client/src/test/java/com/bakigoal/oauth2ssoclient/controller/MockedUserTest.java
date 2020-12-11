package com.bakigoal.oauth2ssoclient.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MockedUserTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser
    public void userMocked() throws Exception {
        mvc.perform(get("/test"))
                .andExpect(content().string("User: user, Roles: [ROLE_USER]"))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "John")
    public void userJohn() throws Exception {
        mvc.perform(get("/test"))
                .andExpect(content().string("User: John, Roles: [ROLE_USER]"))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "Mike", roles = {"ADMIN", "DEVELOPER"})
    public void userMikeRolesAdminAndDeveloper() throws Exception {
        mvc.perform(get("/test"))
                .andExpect(content().string("User: Mike, Roles: [ROLE_ADMIN, ROLE_DEVELOPER]"))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "Mike", authorities = {"ADMIN"})
    public void userMikeRolesAdminAuthority() throws Exception {
        mvc.perform(get("/test"))
                .andExpect(content().string("User: Mike, Roles: [ADMIN]"))
                .andExpect(status().isOk());

    }

    @Test
    public void userInMvc() throws Exception {
        mvc.perform(
                get("/test")
                .with(user("Mike"))
        )
                .andExpect(content().string("User: Mike, Roles: [ROLE_USER]"))
                .andExpect(status().isOk());

    }

    @Test
    public void userDetailsInMvc() throws Exception {
        mvc.perform(
                get("/test")
                        .with(user(createUserDetails()))
        )
                .andExpect(content().string("User: Mike, Roles: [ROLE_ADMIN, ROLE_DEVELOPER, ROLE_TESTER]"))
                .andExpect(status().isOk());

    }

    private UserDetails createUserDetails() {
       return User.withUsername("Mike")
               .password("12345")
               .roles("ADMIN", "DEVELOPER", "TESTER")
               .build();
    }
}