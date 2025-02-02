package com.runner.runs.controllers;

import com.runner.runs.config.SecurityConfig;
import com.runner.runs.services.implementations.RunServiceImpl;
import com.runner.runs.services.implementations.TokenServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Import({SecurityConfig.class, TokenServiceImpl.class, RunServiceImpl.class})
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class RunControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void rootWhenUnauthenticatedThen401() throws Exception {
        this.mvc.perform(get("/api/v1/runs"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void rootWhenAuthenticatedThenReturnRun() throws Exception {
        MvcResult result = this.mvc.perform(post("/api/v1/runs/token")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("testUser", "password")))
                .andExpect(status().isOk())
                .andReturn();

        String token = result.getResponse().getContentAsString();

        this.mvc.perform(get("/api/v1/runs/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Noon Run"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startedOn").value("2024-02-20T06:05:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.completedOn").value("2024-02-20T10:27:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.miles").value(24))
                .andExpect(MockMvcResultMatchers.jsonPath("$.location").value("INDOOR"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(1));
    }

    @Test
    @WithMockUser
    public void rootWithMockUserStatusIsOK() throws Exception {
        this.mvc.perform(get("/api/v1/runs/1")).andExpect(status().isOk());
    }

}