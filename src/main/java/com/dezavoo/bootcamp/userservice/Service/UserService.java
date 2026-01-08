package com.dezavoo.bootcamp.userservice.Service;

import com.dezavoo.bootcamp.userservice.dto.UserRegistrationRequest;
import com.dezavoo.bootcamp.userservice.dto.UserResponse;

public interface UserService {

    UserResponse registerUser(UserRegistrationRequest request);
    
}
