package com.dezavoo.bootcamp.userservice.Service;

import com.dezavoo.bootcamp.userservice.model.User;
import com.dezavoo.bootcamp.userservice.Repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * Service class for User-related operations.
 * Handles business logic and acts as a bridge between Controller and Repository.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Register a new user.
     * 
     * @param user the user to register
     * @return the saved user
     */
    public User registerUser(User user) {
        // Add any business logic here (validation, processing, etc.)
        return userRepository.save(user);
    }
}
