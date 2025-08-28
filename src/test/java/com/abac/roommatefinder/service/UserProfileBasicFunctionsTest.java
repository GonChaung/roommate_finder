package com.abac.roommatefinder.service;

import com.abac.roommatefinder.dto.ProfileRequest;
import com.abac.roommatefinder.entity.Profile;
import com.abac.roommatefinder.entity.User;
import com.abac.roommatefinder.repository.ProfileRepository;
import com.abac.roommatefinder.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Basic (student-level) tests for User Profile functions:
 * 1. createUserProfile  -> ProfileService.createProfile(userId, request)
 * 2. editUserProfile    -> ProfileService.editProfile(userId, request)
 * 3. getUserProfile     -> ProfileService.getProfile(userId)
 * 4. validateProfileData-> ProfileService.validateProfile(request)
 *
 * Each test is short, clear, and focuses on one behavior.
 */
class UserProfileBasicFunctionsTest {

    // Helper to build a valid request quickly
    private ProfileRequest validRequest() {
        ProfileRequest r = new ProfileRequest();
        r.setAge(21);
        r.setGender("F");
        r.setLifestyle("Quiet");
        r.setFaculty("Science");
        r.setLocation("Bangkok");
        r.setBudget(5500.0);
        r.setPetFriendly(true);
        return r;
    }

    // ========== createUserProfile ==========

    @Test
    void createUserProfile_shouldCreateProfile_whenUserExistsAndNoProfileYet() {
        ProfileRepository profileRepo = Mockito.mock(ProfileRepository.class);
        UserRepository userRepo = Mockito.mock(UserRepository.class);

        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        Mockito.when(userRepo.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(profileRepo.findByUserId(userId)).thenReturn(Optional.empty());
        Mockito.when(profileRepo.save(Mockito.any(Profile.class)))
                .thenAnswer(inv -> inv.getArgument(0)); // return saved profile

        ProfileService service = new ProfileService(profileRepo, userRepo);
        Profile result = service.createProfile(userId, validRequest());

        assertNotNull(result);
        assertEquals(user, result.getUser());
        assertEquals(21, result.getAge());
        assertEquals("Bangkok", result.getLocation());
    }

    @Test
    void createUserProfile_shouldThrow_whenUserNotFound() {
        ProfileRepository profileRepo = Mockito.mock(ProfileRepository.class);
        UserRepository userRepo = Mockito.mock(UserRepository.class);

        Mockito.when(userRepo.findById(99L)).thenReturn(Optional.empty());

        ProfileService service = new ProfileService(profileRepo, userRepo);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.createProfile(99L, validRequest()));
        assertEquals("User not found with ID: 99", ex.getMessage());
    }

    // ========== editUserProfile ==========

    @Test
    void editUserProfile_shouldUpdateFields_whenProfileExists() {
        ProfileRepository profileRepo = Mockito.mock(ProfileRepository.class);
        UserRepository userRepo = Mockito.mock(UserRepository.class);

        Long userId = 2L;
        Profile existing = new Profile();
        existing.setAge(19);
        existing.setLocation("Bangkok");

        Mockito.when(profileRepo.findByUserId(userId)).thenReturn(Optional.of(existing));
        Mockito.when(profileRepo.save(existing)).thenReturn(existing);

        ProfileRequest update = new ProfileRequest();
        update.setAge(25); // only updating age in this simple test

        ProfileService service = new ProfileService(profileRepo, userRepo);
        Profile updated = service.editProfile(userId, update);

        assertEquals(25, updated.getAge());
    }

    @Test
    void editUserProfile_shouldThrow_whenProfileMissing() {
        ProfileRepository profileRepo = Mockito.mock(ProfileRepository.class);
        UserRepository userRepo = Mockito.mock(UserRepository.class);

        Mockito.when(profileRepo.findByUserId(55L)).thenReturn(Optional.empty());

        ProfileService service = new ProfileService(profileRepo, userRepo);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.editProfile(55L, validRequest()));
        assertEquals("Profile not found for user ID: 55", ex.getMessage());
    }

    // ========== getUserProfile ==========

    @Test
    void getUserProfile_shouldReturnProfile_whenExists() {
        ProfileRepository profileRepo = Mockito.mock(ProfileRepository.class);
        UserRepository userRepo = Mockito.mock(UserRepository.class);

        Long userId = 7L;
        Profile profile = new Profile();
        profile.setAge(30);

        Mockito.when(profileRepo.findByUserId(userId)).thenReturn(Optional.of(profile));

        ProfileService service = new ProfileService(profileRepo, userRepo);
        Profile result = service.getProfile(userId);

        assertNotNull(result);
        assertEquals(30, result.getAge());
    }

    @Test
    void getUserProfile_shouldThrow_whenNotFound() {
        ProfileRepository profileRepo = Mockito.mock(ProfileRepository.class);
        UserRepository userRepo = Mockito.mock(UserRepository.class);

        Mockito.when(profileRepo.findByUserId(8L)).thenReturn(Optional.empty());

        ProfileService service = new ProfileService(profileRepo, userRepo);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.getProfile(8L));
        assertEquals("Profile not found for user ID: 8", ex.getMessage());
    }

    // ========== validateProfileData ==========

    @Test
    void validateProfileData_shouldReturnTrue_whenAllRequiredFieldsPresent() {
        ProfileRepository profileRepo = Mockito.mock(ProfileRepository.class);
        UserRepository userRepo = Mockito.mock(UserRepository.class);

        ProfileService service = new ProfileService(profileRepo, userRepo);
        assertTrue(service.validateProfile(validRequest()));
    }

    @Test
    void validateProfileData_shouldReturnFalse_whenMissingRequiredField() {
        ProfileRepository profileRepo = Mockito.mock(ProfileRepository.class);
        UserRepository userRepo = Mockito.mock(UserRepository.class);

        ProfileRequest invalid = new ProfileRequest();
        invalid.setAge(21);
        // Missing budget, location, lifestyle -> invalid

        ProfileService service = new ProfileService(profileRepo, userRepo);
        assertFalse(service.validateProfile(invalid));
    }
}