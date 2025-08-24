package com.abac.roommatefinder.controller;

import com.abac.roommatefinder.dto.ProfileRequest;
import com.abac.roommatefinder.entity.Profile;
import com.abac.roommatefinder.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    // createUserProfile(profileData)
    @PostMapping("/{userId}")
    public ResponseEntity<?> create(@PathVariable("userId") Long userId, @Validated @RequestBody ProfileRequest req) {
        Profile p = profileService.createProfile(userId, req);
        return ResponseEntity.ok(p);
    }

    // editUserProfile(userId, updatedProfileData)
    @PutMapping("/{userId}")
    public ResponseEntity<?> edit(@PathVariable("userId") Long userId, @Validated @RequestBody ProfileRequest req) {
        Profile p = profileService.editProfile(userId, req);
        return ResponseEntity.ok(p);
    }

    // getUserProfile(userId)
    @GetMapping("/{userId}")
    public ResponseEntity<?> get(@PathVariable("userId") Long userId) {
        Profile p = profileService.getProfile(userId);
        return ResponseEntity.ok(p);
    }

    // validateProfileData(profileData)
    @PostMapping("/validate")
    public ResponseEntity<?> validate(@Validated @RequestBody ProfileRequest req) {
        boolean ok = profileService.validateProfile(req);
        Map<String, Boolean> response = new HashMap<>();
        response.put("valid", ok);
        return ResponseEntity.ok(response);
    }
}