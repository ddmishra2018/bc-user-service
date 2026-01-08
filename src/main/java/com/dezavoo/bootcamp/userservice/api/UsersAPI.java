package com.dezavoo.bootcamp.userservice.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dezavoo.bootcamp.userservice.dto.UserRegistrationRequest;
import com.dezavoo.bootcamp.userservice.dto.UserResponse;

import jakarta.validation.Valid;

@RequestMapping
public interface UsersAPI {

    @PostMapping("/bootcamp/v1/users/register")
    ResponseEntity<UserResponse> registerUser(
            @Valid @RequestBody UserRegistrationRequest request
    );    
}
