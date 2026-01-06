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

import com.dezavoo.bootcamp.userservice.Service.UserService;
import com.dezavoo.bootcamp.userservice.model.User;
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
        User inputUser = new User();
        inputUser.setName("DD Mishra");
        inputUser.setEmail("DDMishra@google.com");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setName("DD Mishra");
        savedUser.setEmail("DDMishra@google.com");

        // Mock the service behavior
        when(userService.registerUser(any(User.class))).thenReturn(savedUser);

        // 2. Act & Assert: Perform the POST request
        mockMvc.perform(post("/bootcamp/v1/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputUser))) // Convert POJO to JSON
                .andExpect(status().isCreated()) // Verify 201 Status
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L)) // Verify returned JSON fields
                .andExpect(jsonPath("$.name").value("DD Mishra"))
                .andExpect(jsonPath("$.email").value("DDMishra@google.com"));

        // Verify the service was actually called
        Mockito.verify(userService, Mockito.times(1)).registerUser(any(User.class));
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

