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

class ProfileServiceTest {

    @Test
    void createProfileShouldSaveProfile() {
        ProfileRepository profileRepo = Mockito.mock(ProfileRepository.class);
        UserRepository userRepo = Mockito.mock(UserRepository.class);

        User user = new User(); user.setId(1L);
        ProfileRequest req = new ProfileRequest();
        req.setAge(22);
        req.setBudget(6000.0);
        req.setLocation("Bangkok");
        req.setLifestyle("Quiet");
        req.setGender("F");
        req.setFaculty("Science");
        req.setPetFriendly(true);

        Mockito.when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(profileRepo.findByUserId(1L)).thenReturn(Optional.empty());
        // Use Answer to return the correct profile
        Mockito.when(profileRepo.save(Mockito.any(Profile.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ProfileService service = new ProfileService(profileRepo, userRepo);
        Profile result = service.createProfile(1L, req);

        assertEquals(user, result.getUser());
        assertEquals(22, result.getAge());
        assertEquals(6000.0, result.getBudget());
        assertEquals("Bangkok", result.getLocation());
        assertEquals("Quiet", result.getLifestyle());
        assertEquals("F", result.getGender());
        assertEquals("Science", result.getFaculty());
        assertTrue(result.getPetFriendly());
    }

    @Test
    void createProfileShouldThrowIfUserNotFound() {
        ProfileRepository profileRepo = Mockito.mock(ProfileRepository.class);
        UserRepository userRepo = Mockito.mock(UserRepository.class);

        Mockito.when(userRepo.findById(99L)).thenReturn(Optional.empty());
        ProfileRequest req = new ProfileRequest();

        ProfileService service = new ProfileService(profileRepo, userRepo);
        Exception e = assertThrows(IllegalArgumentException.class, () -> service.createProfile(99L, req));
        assertEquals("User not found with ID: 99", e.getMessage());
    }

    @Test
    void createProfileShouldThrowIfProfileAlreadyExists() {
        ProfileRepository profileRepo = Mockito.mock(ProfileRepository.class);
        UserRepository userRepo = Mockito.mock(UserRepository.class);

        User user = new User(); user.setId(1L);
        Profile profile = new Profile(); profile.setUser(user);

        Mockito.when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(profileRepo.findByUserId(1L)).thenReturn(Optional.of(profile));
        ProfileRequest req = new ProfileRequest();

        ProfileService service = new ProfileService(profileRepo, userRepo);
        Exception e = assertThrows(IllegalArgumentException.class, () -> service.createProfile(1L, req));
        assertEquals("Profile already exists for user ID: 1", e.getMessage());
    }

    @Test
    void editProfileShouldSaveProfile() {
        ProfileRepository profileRepo = Mockito.mock(ProfileRepository.class);
        UserRepository userRepo = Mockito.mock(UserRepository.class);

        Profile profile = new Profile();
        profile.setAge(20);

        Mockito.when(profileRepo.findByUserId(1L)).thenReturn(Optional.of(profile));
        Mockito.when(profileRepo.save(profile)).thenReturn(profile);

        ProfileRequest req = new ProfileRequest();
        req.setAge(25);

        ProfileService service = new ProfileService(profileRepo, userRepo);
        Profile result = service.editProfile(1L, req);

        assertEquals(25, result.getAge());
    }

    @Test
    void editProfileShouldThrowIfProfileNotFound() {
        ProfileRepository profileRepo = Mockito.mock(ProfileRepository.class);
        UserRepository userRepo = Mockito.mock(UserRepository.class);

        Mockito.when(profileRepo.findByUserId(10L)).thenReturn(Optional.empty());
        ProfileRequest req = new ProfileRequest();

        ProfileService service = new ProfileService(profileRepo, userRepo);
        Exception e = assertThrows(IllegalArgumentException.class, () -> service.editProfile(10L, req));
        assertEquals("Profile not found for user ID: 10", e.getMessage());
    }

    @Test
    void getProfileShouldReturnProfile() {
        ProfileRepository profileRepo = Mockito.mock(ProfileRepository.class);
        UserRepository userRepo = Mockito.mock(UserRepository.class);

        Profile profile = new Profile();
        Mockito.when(profileRepo.findByUserId(2L)).thenReturn(Optional.of(profile));

        ProfileService service = new ProfileService(profileRepo, userRepo);
        Profile result = service.getProfile(2L);

        assertEquals(profile, result);
    }

    @Test
    void getProfileShouldThrowIfNotFound() {
        ProfileRepository profileRepo = Mockito.mock(ProfileRepository.class);
        UserRepository userRepo = Mockito.mock(UserRepository.class);

        Mockito.when(profileRepo.findByUserId(2L)).thenReturn(Optional.empty());

        ProfileService service = new ProfileService(profileRepo, userRepo);
        Exception e = assertThrows(IllegalArgumentException.class, () -> service.getProfile(2L));
        assertEquals("Profile not found for user ID: 2", e.getMessage());
    }

    @Test
    void validateProfileShouldReturnTrueForValid() {
        ProfileRepository profileRepo = Mockito.mock(ProfileRepository.class);
        UserRepository userRepo = Mockito.mock(UserRepository.class);

        ProfileRequest req = new ProfileRequest();
        req.setAge(20);
        req.setBudget(7000.0);
        req.setLocation("Bangkok");
        req.setLifestyle("Quiet");

        ProfileService service = new ProfileService(profileRepo, userRepo);
        assertTrue(service.validateProfile(req));
    }

    @Test
    void validateProfileShouldReturnFalseForMissingFields() {
        ProfileRepository profileRepo = Mockito.mock(ProfileRepository.class);
        UserRepository userRepo = Mockito.mock(UserRepository.class);

        ProfileRequest req = new ProfileRequest();
        req.setAge(20); // missing budget/location/lifestyle

        ProfileService service = new ProfileService(profileRepo, userRepo);
        assertFalse(service.validateProfile(req));
    }
}