package com.abac.roommatefinder.controller;

import com.abac.roommatefinder.dto.FilterOptions;
import com.abac.roommatefinder.entity.Profile;
import com.abac.roommatefinder.service.MatchService;
import com.abac.roommatefinder.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/match")
public class MatchController {

    private final MatchService matchService;
    private final ProfileService profileService;

    public MatchController(MatchService matchService, ProfileService profileService) {
        this.matchService = matchService;
        this.profileService = profileService;
    }

    // getMatchedProfiles(userId)
    @GetMapping("/{userId}")
    public ResponseEntity<?> matched(@PathVariable Long userId) {
        List<Profile> list = matchService.getMatchedProfiles(userId);
        return ResponseEntity.ok(list);
    }

    // calculateMatchScore(userA, userB)
    @GetMapping("/score")
    public ResponseEntity<?> score(
            @RequestParam("userA") Long userA,
            @RequestParam("userB") Long userB) {
        Profile a = profileService.getProfile(userA);
        Profile b = profileService.getProfile(userB);
        double score = matchService.calculateMatchScore(a, b);
        Map<String, Object> response = new HashMap<>();
        response.put("userA", userA);
        response.put("userB", userB);
        response.put("score", score);
        return ResponseEntity.ok(response);
    }

    // filterProfiles(filterOptions)
    @PostMapping("/filter")
    public ResponseEntity<?> filter(@RequestBody FilterOptions options) {
        List<Profile> list = matchService.filterProfiles(options);
        return ResponseEntity.ok(list);
    }
}
