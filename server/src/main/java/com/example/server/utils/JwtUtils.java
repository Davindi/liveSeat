package com.example.server.utils;
import com.example.server.exception.JwtTokenException;
import com.example.server.models.User;
import com.example.server.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.logging.Logger;

@Component
public class JwtUtils {

    private static final Logger logger = Logger.getLogger(AuthService.class.getName());


    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expirationMs:3600000}")
    private int jwtExpirationMs;

    // Generate JWT token
    public String generateToken(User user) throws JwtTokenException {
        try{
            Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

            return Jwts.builder()
                    .setSubject(user.getUsername())
                    .claim("email", user.getEmail())
                    .claim("role", user.getRole().name())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                    .signWith(key)
                    .compact();
        } catch (Exception e) {
            logger.info("Error generating token for user: " + e.getMessage());
            throw new JwtTokenException("Error generating JWT token", e);
        }
    }
}

