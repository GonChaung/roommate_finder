/*
package com.abac.roommatefinder.integration;

import com.abac.roommatefinder.dto.ProfileRequest;
import com.abac.roommatefinder.dto.RegisterRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

*/
/**
 * Simple "happy path" end-to-end: register -> create profile -> validate profile -> edit profile.
 *//*

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class EndToEndBasicFlowTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    private String json(Object o) throws Exception {
        return mapper.writeValueAsString(o);
    }

    @Test
    @DisplayName("Full flow basic")
    void fullFlow() throws Exception {
        // 1. Register
        RegisterRequest reg = new RegisterRequest();
        reg.setEmail("flow@au.edu");
        reg.setPassword("Flow1!");
        String userBody = mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(reg)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andReturn().getResponse().getContentAsString();
        long userId = mapper.readTree(userBody).get("id").asLong();

        // 2. Create Profile
        ProfileRequest pr = new ProfileRequest();
        pr.setAge(20);
        pr.setGender("M");
        pr.setFaculty("Engineering");
        pr.setLifestyle("night-owl");
        pr.setLocation("Huamak");
        pr.setBudget(6000.0);
        pr.setPetFriendly(false);

        mockMvc.perform(post("/profiles/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(pr)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.budget").value(6000.0));

        // 3. Validate (should be true)
        mockMvc.perform(post("/profiles/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(pr)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valid").value(true));

        // 4. Edit profile (change budget)
        ProfileRequest edit = new ProfileRequest();
        edit.setBudget(6500.0);

        mockMvc.perform(put("/profiles/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(edit)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.budget").value(6500.0));

        // 5. Get profile verify lifestyle unchanged
        mockMvc.perform(get("/profiles/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lifestyle").value("night-owl"));
    }
}*/
