package com.dezavoo.bootcamp.userservice.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import com.dezavoo.bootcamp.userservice.Service.UserService;
import com.dezavoo.bootcamp.userservice.dto.UserRegistrationRequest;
import com.dezavoo.bootcamp.userservice.dto.UserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Unit test for UserController focusing on the Web Layer.
 * @WebMvcTest slices the application to only load the controller and related infrastructure.
 */
@WebMvcTest(UserController.class)
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /bootcamp/v1/users - Should create user and return 201 Created")
    void shouldRegisterUserSuccessfully() throws Exception {
        // 1. Arrange: Prepare mock data
        UserRegistrationRequest inputUser = new UserRegistrationRequest(
                "DDMishra@google.com",
                "DD Mishra",
                "password123",
                "Java"
        );

        UserResponse savedUser = new UserResponse(
                UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
                "DD Mishra",
                "DDMishra@google.com",
                "Java Master Class",
                null,
                null
        );

        // Mock the service behavior
        when(userService.registerUser(any(UserRegistrationRequest.class))).thenReturn(savedUser);

        // 2. Act & Assert: Perform the POST request
        mockMvc.perform(post("/bootcamp/v1/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputUser))) // Convert POJO to JSON
                .andExpect(status().isCreated()) // Verify 201 Status
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userId").value("550e8400-e29b-41d4-a716-446655440000")) // Verify returned JSON fields
                .andExpect(jsonPath("$.name").value("DD Mishra"))
                .andExpect(jsonPath("$.emailId").value("DDMishra@google.com"))
                .andExpect(jsonPath("$.courseOpted").value("Java Master Class"));

        // Verify the service was actually called
        Mockito.verify(userService, Mockito.times(1)).registerUser(any(UserRegistrationRequest.class));
    }

    @Test
    @DisplayName("POST /bootcamp/v1/users/register - Should return 400 Bad Request on empty body")
    void shouldReturnBadRequestWhenBodyIsMissing() throws Exception {
        mockMvc.perform(post("/bootcamp/v1/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isBadRequest());
    }
}

