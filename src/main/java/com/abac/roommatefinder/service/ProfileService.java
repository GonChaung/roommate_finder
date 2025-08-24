package com.abac.roommatefinder.service;

import com.abac.roommatefinder.dto.ProfileRequest;
import com.abac.roommatefinder.entity.Profile;
import com.abac.roommatefinder.entity.User;
import com.abac.roommatefinder.repository.ProfileRepository;
import com.abac.roommatefinder.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public ProfileService(ProfileRepository profileRepository, UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    public Profile createProfile(Long userId, ProfileRequest req) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Profile p = new Profile();
        copy(req, p);
        p.setUser(user);
        user.setProfile(p);
        return profileRepository.save(p);
    }

    public Profile editProfile(Long userId, ProfileRequest req) {
        Profile p = profileRepository.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("Profile not found"));
        copy(req, p);
        return profileRepository.save(p);
    }

    public Profile getProfile(Long userId) {
        return profileRepository.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("Profile not found"));
    }

    public boolean validateProfile(ProfileRequest req) {
        // Minimal validation: required fields must be non-null
        return req.getAge()!=null && req.getBudget()!=null && req.getLocation()!=null && req.getLifestyle()!=null;
    }

    private void copy(ProfileRequest req, Profile p) {
        p.setAge(req.getAge());
        p.setGender(req.getGender());
        p.setFaculty(req.getFaculty());
        p.setLifestyle(req.getLifestyle());
        p.setLocation(req.getLocation());
        p.setBudget(req.getBudget());
        p.setPetFriendly(req.getPetFriendly());
    }
}
