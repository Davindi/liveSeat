package com.example.server.controllers;

import com.example.server.dto.SignInDto;
import com.example.server.dto.SignUpDto;
import com.example.server.exception.JwtTokenException;
import com.example.server.exception.UserAlreadyExistsException;
import com.example.server.models.User;
import com.example.server.repository.UserRepository;
import com.example.server.service.AuthService;
import com.example.server.utils.JwtUtils;
import com.example.server.utils.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication")

public class AuthController {
    private final AuthService authService;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

    @Autowired
    public AuthController(AuthService authService, JwtUtils jwtUtils, UserRepository userRepository) {
        this.authService = authService;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
    }

    // Sign UP: Authenticate the user and return JWT token
    @Operation(summary = "Sign up a new user")
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpDto signUpRequest) {
        try {
            User user = authService.register(signUpRequest);
            String token = jwtUtils.generateToken(user);
            return ResponseEntity.ok(new MessageResponse( "User registered successfully",token));
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(400).body(new MessageResponse("User already exists"));
        } catch (JwtTokenException e) {
            return ResponseEntity.status(500).body(new MessageResponse("Error generating token"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new MessageResponse("Internal server error"));
        }
    }

    // Sign IN: Authenticate the user and return JWT token
    @Operation(summary = "Sign in an existing user")
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody SignInDto signInRequest) {
        try {
            Boolean isAuthenticated = authService.loginUser(signInRequest);
            if (isAuthenticated) {
                User user = userRepository.findByUsername(signInRequest.getUsername()).get();
                String token = jwtUtils.generateToken(user);
                return ResponseEntity.ok(new MessageResponse( "Logging succeed",token));
            } else {
                return ResponseEntity.status(401).body(new MessageResponse("Invalid credentials"));
            }
        }catch (Exception e) {
            return ResponseEntity.status(500).body(new MessageResponse("Internal server error"));
        }
    }

}
