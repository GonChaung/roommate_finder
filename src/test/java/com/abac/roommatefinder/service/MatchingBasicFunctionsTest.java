package com.abac.roommatefinder.service;

import com.abac.roommatefinder.dto.FilterOptions;
import com.abac.roommatefinder.entity.Profile;
import com.abac.roommatefinder.entity.User;
import com.abac.roommatefinder.repository.ProfileRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Basic (student-level) unit tests for matching functions:
 *  1. getMatchedProfiles(userId)
 *  2. calculateMatchScore(userA, userB)
 *  3. filterProfiles(filterOptions)
 *
 * The tests are intentionally simple, with clear setup and assertions.
 *
 * NOTE: These tests assume the MatchService implementation that:
 *  - Excludes the requesting user's own profile from matches.
 *  - Sorts matches from highest score to lowest.
 *  - Uses "strict" filtering: if a filter field is set and the profile field is null or different, it is excluded.
 */
class MatchingBasicFunctionsTest {

    // Helper to create a profile quickly
    private Profile profile(long userId, String location, String lifestyle, Double budget, Boolean petFriendly) {
        User u = new User();
        u.setId(userId);
        Profile p = new Profile();
        p.setUser(u);
        p.setLocation(location);
        p.setLifestyle(lifestyle);
        p.setBudget(budget);
        p.setPetFriendly(petFriendly);
        return p;
    }

    // ============ getMatchedProfiles() ============

    @Test
    void getMatchedProfiles_shouldReturnSortedMatchesAndExcludeSelf() {
        ProfileRepository repo = Mockito.mock(ProfileRepository.class);

        Profile self = profile(1L, "Bangkok", "Quiet", 6000.0, true);
        Profile good = profile(2L, "Bangkok", "Quiet", 6100.0, true);      // Perfect (expected top)
        Profile medium = profile(3L, "Bangkok", "Lively", 6500.0, true);   // Location + budget + pet
        Profile lower = profile(4L, "Phuket", "Quiet", 6000.0, true);      // Lifestyle + budget + pet
        Profile low = profile(5L, "Phuket", "Lively", 9000.0, false);      // Probably low score

        List<Profile> all = Arrays.asList(self, good, medium, lower, low);

        Mockito.when(repo.findByUserId(1L)).thenReturn(Optional.of(self));
        Mockito.when(repo.findAll()).thenReturn(all);

        MatchService service = new MatchService(repo);
        List<Profile> result = service.getMatchedProfiles(1L);

        // 1. Self should be excluded
        assertEquals(4, result.size());
        assertTrue(result.stream().noneMatch(p -> p.getUser().getId() == 1L));

        // 2. First one should be the "good" perfect match (highest score)
        assertEquals(2L, result.get(0).getUser().getId());

        // 3. Scores are non-increasing
        double previous = Double.MAX_VALUE;
        for (Profile p : result) {
            double score = service.calculateMatchScore(self, p);
            assertTrue(score <= previous);
            previous = score;
        }
    }

    @Test
    void getMatchedProfiles_shouldThrowWhenUserProfileMissing() {
        ProfileRepository repo = Mockito.mock(ProfileRepository.class);
        Mockito.when(repo.findByUserId(99L)).thenReturn(Optional.empty());

        MatchService service = new MatchService(repo);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.getMatchedProfiles(99L));
        assertEquals("Profile not found", ex.getMessage());
    }

    // ============ calculateMatchScore() ============

    @Test
    void calculateMatchScore_shouldReturn100ForPerfectMatch() {
        ProfileRepository repo = Mockito.mock(ProfileRepository.class);
        MatchService service = new MatchService(repo);

        Profile a = profile(1L, "Bangkok", "Quiet", 6000.0, true);
        Profile b = profile(2L, "Bangkok", "Quiet", 6100.0, true); // within budget range

        double score = service.calculateMatchScore(a, b);
        assertEquals(100.0, score);
    }

    @Test
    void calculateMatchScore_shouldReturnPartialScore() {
        ProfileRepository repo = Mockito.mock(ProfileRepository.class);
        MatchService service = new MatchService(repo);

        Profile a = profile(1L, "Bangkok", "Quiet", 6000.0, true);
        Profile b = profile(2L, "Bangkok", "Lively", 6500.0, false);

        // Expected: +30 (location) +0 (lifestyle) +20 (budget within 1000) +0 (pet) = 50
        double score = service.calculateMatchScore(a, b);
        assertEquals(50.0, score);
    }

    // ============ filterProfiles() ============

    @Test
    void filterProfiles_shouldReturnOnlyProfilesMatchingAllFilters() {
        ProfileRepository repo = Mockito.mock(ProfileRepository.class);

        Profile keep = profile(1L, "Bangkok", "Quiet", 6000.0, true);
        Profile wrongLocation = profile(2L, "Phuket", "Quiet", 6000.0, true);
        Profile wrongLifestyle = profile(3L, "Bangkok", "Lively", 6000.0, true);
        Profile outsideBudget = profile(4L, "Bangkok", "Quiet", 9000.0, true);
        Profile petMismatch = profile(5L, "Bangkok", "Quiet", 6200.0, false);

        Mockito.when(repo.findAll()).thenReturn(Arrays.asList(
                keep, wrongLocation, wrongLifestyle, outsideBudget, petMismatch
        ));

        FilterOptions f = new FilterOptions();
        f.setLocation("Bangkok");
        f.setLifestyle("Quiet");
        f.setMinBudget(5000.0);
        f.setMaxBudget(8000.0);
        f.setPetFriendly(true);

        MatchService service = new MatchService(repo);
        List<Profile> result = service.filterProfiles(f);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getUser().getId());
    }

    @Test
    void filterProfiles_withNoFiltersShouldReturnAllProfiles() {
        ProfileRepository repo = Mockito.mock(ProfileRepository.class);

        Profile p1 = profile(1L, "Bangkok", "Quiet", 6000.0, true);
        Profile p2 = profile(2L, "Phuket", "Lively", 4000.0, false);

        Mockito.when(repo.findAll()).thenReturn(Arrays.asList(p1, p2));

        FilterOptions f = new FilterOptions(); // all null -> no filtering

        MatchService service = new MatchService(repo);
        List<Profile> result = service.filterProfiles(f);

        assertEquals(2, result.size());
        assertTrue(result.contains(p1));
        assertTrue(result.contains(p2));
    }
}