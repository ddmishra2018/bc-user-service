package com.dezavoo.bootcamp.userservice.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.dezavoo.bootcamp.userservice.Service.UserService;
import com.dezavoo.bootcamp.userservice.api.UsersAPI;
import com.dezavoo.bootcamp.userservice.dto.UserRegistrationRequest;
import com.dezavoo.bootcamp.userservice.dto.UserResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController implements UsersAPI {
    
    private final UserService userService;


    /**
     * Register a new user.
     * 
     * @param user the user to register
     * @return ResponseEntity with the saved user and 201 Created status
     */
    @Override
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
        UserResponse savedUser = userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
}

