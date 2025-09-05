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

class AuthServiceTest {

    @Test
    void registerShouldSaveUser() {
        UserRepository repo = Mockito.mock(UserRepository.class);
        RegisterRequest req = new RegisterRequest();
        req.setEmail("user@au.edu");
        req.setPassword("password123");

        Mockito.when(repo.existsByEmail("user@au.edu")).thenReturn(false);
        User user = new User("user@au.edu", Integer.toHexString("password123".hashCode()));
        Mockito.when(repo.save(Mockito.any(User.class))).thenReturn(user);

        AuthService service = new AuthService(repo);
        User result = service.register(req);

        assertEquals("user@au.edu", result.getEmail());
        assertEquals(Integer.toHexString("password123".hashCode()), result.getPasswordHash());
    }

    @Test
    void registerShouldThrowIfNotAbacEmail() {
        UserRepository repo = Mockito.mock(UserRepository.class);
        RegisterRequest req = new RegisterRequest();
        req.setEmail("user@gmail.com");
        req.setPassword("password123");

        AuthService service = new AuthService(repo);
        Exception e = assertThrows(IllegalArgumentException.class, () -> service.register(req));
        assertEquals("Email must be an ABAC/au.edu email.", e.getMessage());
    }

    @Test
    void registerShouldThrowIfEmailExists() {
        UserRepository repo = Mockito.mock(UserRepository.class);
        RegisterRequest req = new RegisterRequest();
        req.setEmail("user@au.edu");
        req.setPassword("password123");

        Mockito.when(repo.existsByEmail("user@au.edu")).thenReturn(true);

        AuthService service = new AuthService(repo);
        Exception e = assertThrows(IllegalArgumentException.class, () -> service.register(req));
        assertEquals("Email already registered.", e.getMessage());
    }

    @Test
    void loginShouldReturnTokenForValidUser() {
        UserRepository repo = Mockito.mock(UserRepository.class);
        LoginRequest req = new LoginRequest();
        req.setEmail("user@au.edu");
        req.setPassword("password123");
        User user = new User("user@au.edu", Integer.toHexString("password123".hashCode()));

        Mockito.when(repo.findByEmail("user@au.edu")).thenReturn(Optional.of(user));

        AuthService service = new AuthService(repo);
        String token = service.login(req);

        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    void loginShouldThrowIfUserNotFound() {
        UserRepository repo = Mockito.mock(UserRepository.class);
        LoginRequest req = new LoginRequest();
        req.setEmail("user@au.edu");
        req.setPassword("password123");

        Mockito.when(repo.findByEmail("user@au.edu")).thenReturn(Optional.empty());

        AuthService service = new AuthService(repo);
        Exception e = assertThrows(IllegalArgumentException.class, () -> service.login(req));
        assertEquals("Invalid credentials.", e.getMessage());
    }

    @Test
    void loginShouldThrowIfBanned() {
        UserRepository repo = Mockito.mock(UserRepository.class);
        LoginRequest req = new LoginRequest();
        req.setEmail("user@au.edu");
        req.setPassword("password123");
        User user = new User("user@au.edu", Integer.toHexString("password123".hashCode()));
        user.setBanned(true);

        Mockito.when(repo.findByEmail("user@au.edu")).thenReturn(Optional.of(user));

        AuthService service = new AuthService(repo);
        Exception e = assertThrows(IllegalStateException.class, () -> service.login(req));
        assertEquals("User is banned.", e.getMessage());
    }

    @Test
    void loginShouldThrowIfPasswordIncorrect() {
        UserRepository repo = Mockito.mock(UserRepository.class);
        LoginRequest req = new LoginRequest();
        req.setEmail("user@au.edu");
        req.setPassword("wrongpassword");
        User user = new User("user@au.edu", Integer.toHexString("password123".hashCode()));

        Mockito.when(repo.findByEmail("user@au.edu")).thenReturn(Optional.of(user));

        AuthService service = new AuthService(repo);
        Exception e = assertThrows(IllegalArgumentException.class, () -> service.login(req));
        assertEquals("Invalid credentials.", e.getMessage());
    }

    @Test
    void resetPasswordShouldDoNothingIfEmailExists() {
        UserRepository repo = Mockito.mock(UserRepository.class);
        ResetPasswordRequest req = new ResetPasswordRequest();
        req.setEmail("user@au.edu");

        Mockito.when(repo.existsByEmail("user@au.edu")).thenReturn(true);

        AuthService service = new AuthService(repo);
        assertDoesNotThrow(() -> service.resetPassword(req));
    }

    @Test
    void resetPasswordShouldThrowIfEmailNotFound() {
        UserRepository repo = Mockito.mock(UserRepository.class);
        ResetPasswordRequest req = new ResetPasswordRequest();
        req.setEmail("notfound@au.edu");

        Mockito.when(repo.existsByEmail("notfound@au.edu")).thenReturn(false);

        AuthService service = new AuthService(repo);
        Exception e = assertThrows(IllegalArgumentException.class, () -> service.resetPassword(req));
        assertEquals("Email not found.", e.getMessage());
    }
}*/
