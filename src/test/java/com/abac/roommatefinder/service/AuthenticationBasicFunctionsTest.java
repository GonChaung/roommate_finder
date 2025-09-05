/*
package com.abac.roommatefinder.service;

import com.abac.roommatefinder.dto.LoginRequest;
import com.abac.roommatefinder.dto.RegisterRequest;
import com.abac.roommatefinder.dto.ResetPasswordRequest;
import com.abac.roommatefinder.entity.User;
import com.abac.roommatefinder.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

*/
/**
 * Basic (student-level) unit tests for Authentication functions:
 *  1. registerUser(email, password)
 *  2. loginUser(email, password)
 *  3. logoutUser()
 *  4. resetPassword(email)
 *
 * Assumptions about AuthService (adjust if your actual method names differ):
 *  - User register(RegisterRequest req)
 *  - String login(LoginRequest req)  (returns a session/token string)
 *  - void logout(String token)
 *  - void resetPassword(ResetPasswordRequest req)
 *  - Validation: email must end with "@au.edu"
 *  - Password stored as a hash (simplified here with hashCode or similar)
 *  - Duplicate registration throws IllegalArgumentException
 *  - Invalid credentials throw IllegalArgumentException("Invalid credentials.")
 *  - Non‑existent email in resetPassword throws IllegalArgumentException("Email not found.")
 *
 * KEEPING TESTS SIMPLE: Only core success & failure cases.
 *//*

class AuthenticationBasicFunctionsTest {

    // Helper to build a user object (simple)
    private User user(String email, String rawPassword) {
        User u = new User();
        u.setEmail(email);
        // Simplified passwordHash expectation (mirror AuthService hashing logic if different)
        u.setPasswordHash(Integer.toHexString(rawPassword.hashCode()));
        return u;
    }

    // ---------- registerUser tests ----------

    @Test
    void registerUser_shouldCreateNewAccount_whenEmailValidAndNotExists() {
        UserRepository userRepo = Mockito.mock(UserRepository.class);

        String email = "student@au.edu";
        String password = "StrongPass1";

        Mockito.when(userRepo.existsByEmail(email)).thenReturn(false);
        Mockito.when(userRepo.save(Mockito.any(User.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        AuthService service = new AuthService(userRepo);
        RegisterRequest req = new RegisterRequest();
        req.setEmail(email);
        req.setPassword(password);

        User created = service.register(req);

        assertEquals(email, created.getEmail());
        assertEquals(Integer.toHexString(password.hashCode()), created.getPasswordHash());
    }

    @Test
    void registerUser_shouldThrow_whenEmailDomainInvalid() {
        UserRepository userRepo = Mockito.mock(UserRepository.class);
        AuthService service = new AuthService(userRepo);

        RegisterRequest req = new RegisterRequest();
        req.setEmail("someone@gmail.com");
        req.setPassword("pass123");

        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> service.register(req));
        assertEquals("Email must be an ABAC/au.edu email.", ex.getMessage());
    }

    @Test
    void registerUser_shouldThrow_whenEmailAlreadyExists() {
        UserRepository userRepo = Mockito.mock(UserRepository.class);
        String email = "student@au.edu";
        Mockito.when(userRepo.existsByEmail(email)).thenReturn(true);

        AuthService service = new AuthService(userRepo);
        RegisterRequest req = new RegisterRequest();
        req.setEmail(email);
        req.setPassword("pass123");

        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> service.register(req));
        assertEquals("Email already registered.", ex.getMessage());
    }

    // ---------- loginUser tests ----------

    @Test
    void loginUser_shouldReturnToken_whenCredentialsCorrect() {
        UserRepository userRepo = Mockito.mock(UserRepository.class);
        String email = "student@au.edu";
        String password = "MyPass!";
        User stored = user(email, password);

        Mockito.when(userRepo.findByEmail(email)).thenReturn(Optional.of(stored));

        AuthService service = new AuthService(userRepo);
        LoginRequest req = new LoginRequest();
        req.setEmail(email);
        req.setPassword(password);

        String token = service.login(req);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void loginUser_shouldThrow_whenPasswordIncorrect() {
        UserRepository userRepo = Mockito.mock(UserRepository.class);
        String email = "student@au.edu";
        User stored = user(email, "Correct123");

        Mockito.when(userRepo.findByEmail(email)).thenReturn(Optional.of(stored));

        AuthService service = new AuthService(userRepo);
        LoginRequest req = new LoginRequest();
        req.setEmail(email);
        req.setPassword("WrongPassword");

        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> service.login(req));
        assertEquals("Invalid credentials.", ex.getMessage());
    }

    @Test
    void loginUser_shouldThrow_whenUserNotFound() {
        UserRepository userRepo = Mockito.mock(UserRepository.class);
        Mockito.when(userRepo.findByEmail("missing@au.edu")).thenReturn(Optional.empty());

        AuthService service = new AuthService(userRepo);
        LoginRequest req = new LoginRequest();
        req.setEmail("missing@au.edu");
        req.setPassword("whatever");

        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> service.login(req));
        assertEquals("Invalid credentials.", ex.getMessage());
    }

    // ---------- logoutUser tests ----------

    @Test
    void logoutUser_shouldNotThrow_evenIfTokenUnknown() {
        UserRepository userRepo = Mockito.mock(UserRepository.class);
        AuthService service = new AuthService(userRepo);

        // Assuming logout just invalidates token (no repository needed here)
        assertDoesNotThrow(() -> service.logout("some-session-token"));
    }

    // ---------- resetPassword tests ----------

    @Test
    void resetPassword_shouldSucceed_whenEmailExists() {
        UserRepository userRepo = Mockito.mock(UserRepository.class);
        String email = "student@au.edu";
        Mockito.when(userRepo.existsByEmail(email)).thenReturn(true);

        AuthService service = new AuthService(userRepo);
        ResetPasswordRequest req = new ResetPasswordRequest();
        req.setEmail(email);

        assertDoesNotThrow(() -> service.resetPassword(req));
    }

    @Test
    void resetPassword_shouldThrow_whenEmailNotFound() {
        UserRepository userRepo = Mockito.mock(UserRepository.class);
        String email = "unknown@au.edu";
        Mockito.when(userRepo.existsByEmail(email)).thenReturn(false);

        AuthService service = new AuthService(userRepo);
        ResetPasswordRequest req = new ResetPasswordRequest();
        req.setEmail(email);

        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> service.resetPassword(req));
        assertEquals("Email not found.", ex.getMessage());
    }
}*/
