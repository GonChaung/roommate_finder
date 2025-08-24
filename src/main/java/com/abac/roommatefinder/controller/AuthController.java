package com.abac.roommatefinder.controller;

import com.abac.roommatefinder.dto.LoginRequest;
import com.abac.roommatefinder.dto.RegisterRequest;
import com.abac.roommatefinder.dto.ResetPasswordRequest;
import com.abac.roommatefinder.entity.User;
import com.abac.roommatefinder.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // registerUser(email, password)
    @PostMapping("/register")
    public ResponseEntity<?> register(@Validated @RequestBody RegisterRequest req) {
        User u = authService.register(req);
        return ResponseEntity.ok(u);
    }

    // loginUser(email, password)
    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody LoginRequest req) {
        String token = authService.login(req);
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    // logoutUser()
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(value = "X-Token", required = false) String token) {
        authService.logout(token);
        return ResponseEntity.ok().build();
    }

    // resetPassword(email)
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Validated @RequestBody ResetPasswordRequest req) {
        authService.resetPassword(req);
        return ResponseEntity.ok().build();
    }
}
