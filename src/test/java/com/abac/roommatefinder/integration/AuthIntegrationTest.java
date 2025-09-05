/*
package com.abac.roommatefinder.integration;

import com.abac.roommatefinder.dto.LoginRequest;
import com.abac.roommatefinder.dto.RegisterRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.var;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

*/
/**
 * Basic (student-level) integration tests for Authentication endpoints.
 *
 * NOTE ABOUT ERROR STATUS:
 *  The service throws IllegalArgumentException / IllegalStateException.
 *  Without a @ControllerAdvice these usually become HTTP 500 responses.
 *  If later you add exception handlers mapping them to 400/403, adjust the expected codes.
 *//*

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // isolate tests
class AuthIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private String toJson(Object o) throws Exception {
        return objectMapper.writeValueAsString(o);
    }

    @Test
    @DisplayName("Register valid ABAC email -> 200 OK returns user JSON with id")
    void register_valid() throws Exception {
        RegisterRequest req = new RegisterRequest();
        req.setEmail("student1@au.edu");
        req.setPassword("Pass123");

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.email").value("student1@au.edu"));
    }

    @Test
    @DisplayName("Register invalid (gmail) -> error (likely 500 without handler)")
    void register_invalidDomain() throws Exception {
        RegisterRequest req = new RegisterRequest();
        req.setEmail("abc@gmail.com");
        req.setPassword("x");

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(req)))
                .andExpect(status().is5xxServerError()); // change to isBadRequest() if you add handler
    }

    @Test
    @DisplayName("Duplicate email registration -> second call fails")
    void register_duplicate() throws Exception {
        RegisterRequest req = new RegisterRequest();
        req.setEmail("dup@au.edu");
        req.setPassword("p1");

        // first success
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(req)))
                .andExpect(status().isOk());

        // second fails
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(req)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("Login success returns token field")
    void login_success() throws Exception {
        // register first
        RegisterRequest reg = new RegisterRequest();
        reg.setEmail("loginuser@au.edu");
        reg.setPassword("Secret!");
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(reg)))
                .andExpect(status().isOk());

        LoginRequest login = new LoginRequest();
        login.setEmail("loginuser@au.edu");
        login.setPassword("Secret!");

        String body = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(login)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andReturn().getResponse().getContentAsString();

        Map<?,?> map = objectMapper.readValue(body, Map.class);
        assertThat(map.get("token")).isNotNull();
    }

    @Test
    @DisplayName("Login wrong password -> error")
    void login_wrongPassword() throws Exception {
        // register
        RegisterRequest reg = new RegisterRequest();
        reg.setEmail("pwtest@au.edu");
        reg.setPassword("AAA");
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(reg)))
                .andExpect(status().isOk());

        LoginRequest bad = new LoginRequest();
        bad.setEmail("pwtest@au.edu");
        bad.setPassword("BBB");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(bad)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("Reset password unknown email -> error")
    void reset_unknown() throws Exception {
        var req = new com.abac.roommatefinder.dto.ResetPasswordRequest();
        req.setEmail("nope@au.edu");

        mockMvc.perform(post("/auth/reset-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(req)))
                .andExpect(status().is5xxServerError());
    }
}*/
