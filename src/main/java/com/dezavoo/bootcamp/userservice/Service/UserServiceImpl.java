package com.dezavoo.bootcamp.userservice.Service;

import com.dezavoo.bootcamp.userservice.model.User;
import com.dezavoo.bootcamp.userservice.Repository.UserRepository;
import com.dezavoo.bootcamp.userservice.dto.UserRegistrationRequest;
import com.dezavoo.bootcamp.userservice.dto.UserResponse;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

/**
 * Service class for User-related operations.
 * Handles business logic and acts as a bridge between Controller and Repository.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Register a new user.
     * 
     * @param user the user to register
     * @return the saved user
     */
    @Override
    public UserResponse registerUser(UserRegistrationRequest request) {

        // Add any business logic here (validation, processing, etc.)
        //return userRepository.save(request);
        return buildResponse(
                UUID.randomUUID(),
                request.name(),
                request.email(),
                request.courseOpted()
        );
    }

    private UserResponse buildResponse(UUID id, String name, String email, String course) {
        return new UserResponse(id, name, email, course, OffsetDateTime.now(), OffsetDateTime.now());
    }
}
