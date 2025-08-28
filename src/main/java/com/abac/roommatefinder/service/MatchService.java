package com.abac.roommatefinder.service;

import com.abac.roommatefinder.dto.FilterOptions;
import com.abac.roommatefinder.entity.Profile;
import com.abac.roommatefinder.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MatchService {
    private final ProfileRepository profileRepository;

    public MatchService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public List<Profile> getMatchedProfiles(Long userId) {
        Profile user = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found"));
        return profileRepository.findAll().stream()
                .filter(p -> !Objects.equals(p.getUser().getId(), userId))
                .sorted(Comparator.comparingDouble((Profile p) -> -calculateMatchScore(user, p)))
                .collect(Collectors.toList());
    }

    public double calculateMatchScore(Profile a, Profile b) {
        double score = 0;
        if (a.getLocation()!=null && a.getLocation().equalsIgnoreCase(b.getLocation())) score += 30;
        if (a.getLifestyle()!=null && a.getLifestyle().equalsIgnoreCase(b.getLifestyle())) score += 30;
        if (a.getBudget()!=null && b.getBudget()!=null && Math.abs(a.getBudget() - b.getBudget()) <= 1000) score += 20;
        if (a.getPetFriendly()!=null && b.getPetFriendly()!=null && a.getPetFriendly().equals(b.getPetFriendly())) score += 20;
        return score;
    }

    public List<Profile> filterProfiles(FilterOptions f) {
        return profileRepository.findAll().stream().filter(p -> {
            if (f.getLocation() != null) {
                if (p.getLocation() == null || !p.getLocation().equalsIgnoreCase(f.getLocation())) return false;
            }
            if (f.getLifestyle() != null) {
                if (p.getLifestyle() == null || !p.getLifestyle().equalsIgnoreCase(f.getLifestyle())) return false;
            }
            if (f.getPetFriendly() != null) {
                if (p.getPetFriendly() == null || !p.getPetFriendly().equals(f.getPetFriendly())) return false;
            }
            if (f.getMinBudget() != null) {
                if (p.getBudget() == null || p.getBudget() < f.getMinBudget()) return false;
            }
            if (f.getMaxBudget() != null) {
                if (p.getBudget() == null || p.getBudget() > f.getMaxBudget()) return false;
            }
            return true;
        }).collect(Collectors.toList());
    }
}