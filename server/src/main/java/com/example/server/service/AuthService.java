package com.example.server.service;

import com.example.server.controllers.ActivityController;
import com.example.server.dto.SignUpDto;
import com.example.server.dto.SignInDto;
import com.example.server.exception.UserAlreadyExistsException;
import com.example.server.models.User;
import com.example.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class AuthService {

    private static final Logger logger = Logger.getLogger(AuthService.class.getName());


    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private final ActivityController activityController;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, ActivityController activityController) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.activityController = activityController;
    }

    public User register(SignUpDto signUpDto) {

        // Check if user already exists by username or email
        if (userRepository.existsByUsername(signUpDto.getUsername())) {
            throw new UserAlreadyExistsException("Username is already taken");
        }
        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            throw new UserAlreadyExistsException("Email is already taken");
        }

        String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());
        User user = new User(signUpDto.getUsername(), encodedPassword, signUpDto.getEmail(), signUpDto.getRole(), signUpDto.getFirstname());
        userRepository.save(user);
        // Broadcast activity
        activityController.broadcastActivity("New " + signUpDto.getUsername() +"user registered");
        return user;
    }


    public Boolean loginUser(SignInDto signInDto) {
        Optional<User> user = userRepository.findByUsername(signInDto.getUsername());
        if (user.isPresent() && passwordEncoder.matches(signInDto.getPassword(), user.get().getPassword())) {
            activityController.broadcastActivity(signInDto.getUsername()+" loged in successfully " );
            return true;
        }else {
            return false;
        }
    }
}
