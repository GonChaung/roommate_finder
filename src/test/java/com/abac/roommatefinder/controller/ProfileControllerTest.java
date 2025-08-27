package com.abac.roommatefinder.controller;

import com.abac.roommatefinder.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProfileController.class)
class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfileService profileService;

    @Test
    void createProfileShouldReturnOk() throws Exception {
        String content = "{\"age\":21,\"gender\":\"M\",\"faculty\":\"Engineering\",\"lifestyle\":\"night-owl\",\"location\":\"Hua Mak\",\"budget\":6000,\"petFriendly\":true}";
        mockMvc.perform(post("/profiles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk());
    }
}
