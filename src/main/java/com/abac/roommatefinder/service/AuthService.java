package com.abac.roommatefinder.service;

import com.abac.roommatefinder.dto.LoginRequest;
import com.abac.roommatefinder.dto.RegisterRequest;
import com.abac.roommatefinder.dto.ResetPasswordRequest;
import com.abac.roommatefinder.entity.User;
import com.abac.roommatefinder.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class AuthService {
    private final UserRepository userRepository;

    private static final Pattern ABAC_EMAIL =
            Pattern.compile("^[^@]+@au\\.edu$");


    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(RegisterRequest req) {
        if (!ABAC_EMAIL.matcher(req.getEmail()).find()) {
            throw new IllegalArgumentException("Email must be an ABAC/au.edu email.");
        }
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email already registered.");
        }
        // Very simple hashing substitute for demo (not Spring Security)
        String hash = Integer.toHexString(req.getPassword().hashCode());
        User user = new User(req.getEmail(), hash);
        return userRepository.save(user);
    }

    public String login(LoginRequest req) {
        Optional<User> opt = userRepository.findByEmail(req.getEmail());
        if (!opt.isPresent()) {
            throw new IllegalArgumentException("Invalid credentials.");
        }
        User user = opt.get();
        if (user.isBanned()) {
            throw new IllegalStateException("User is banned.");
        }
        // Simple hash (not secure, for testing only)
        String hash = Integer.toHexString(req.getPassword().hashCode());
        if (!hash.equals(user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid credentials.");
        }
        // Issue fake token (for testing only)
        return UUID.randomUUID().toString();
    }

    public void logout(String token) {
        // no-op for testing; client discards token
    }

    public void resetPassword(ResetPasswordRequest req) {
        // stub for testing; in real app send an email
        if (!userRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email not found.");
        }
    }
}
