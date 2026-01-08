package com.dezavoo.bootcamp.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegistrationRequest (

    @Email@NotBlank String emailId,
    @NotBlank String name,
    @Size(min = 8) String password,
    @NotBlank String courseOpted
   
) {}
