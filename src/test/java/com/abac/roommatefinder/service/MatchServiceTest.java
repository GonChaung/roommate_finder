package com.abac.roommatefinder.service;

import com.abac.roommatefinder.dto.FilterOptions;
import com.abac.roommatefinder.entity.Profile;
import com.abac.roommatefinder.entity.User;
import com.abac.roommatefinder.repository.ProfileRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MatchServiceTest {

    @Test
    void getMatchedProfilesShouldReturnSortedList() {
        ProfileRepository profileRepo = Mockito.mock(ProfileRepository.class);

        User user = new User(); user.setId(1L);
        Profile self = new Profile(); self.setUser(user);
        self.setLocation("Bangkok"); self.setLifestyle("Quiet"); self.setBudget(6000.0); self.setPetFriendly(true);

        User u2 = new User(); u2.setId(2L);
        Profile p2 = new Profile(); p2.setUser(u2);
        p2.setLocation("Bangkok"); p2.setLifestyle("Quiet"); p2.setBudget(6100.0); p2.setPetFriendly(true);

        User u3 = new User(); u3.setId(3L);
        Profile p3 = new Profile(); p3.setUser(u3);
        p3.setLocation("Bangkok"); p3.setLifestyle("Lively"); p3.setBudget(6300.0); p3.setPetFriendly(false);

        List<Profile> allProfiles = Arrays.asList(self, p2, p3);

        Mockito.when(profileRepo.findByUserId(1L)).thenReturn(Optional.of(self));
        Mockito.when(profileRepo.findAll()).thenReturn(allProfiles);

        MatchService service = new MatchService(profileRepo);
        List<Profile> matches = service.getMatchedProfiles(1L);

        assertEquals(2, matches.size()); // Excludes self
        assertEquals(p2, matches.get(0)); // Best match
    }

    @Test
    void getMatchedProfilesShouldThrowIfProfileNotFound() {
        ProfileRepository profileRepo = Mockito.mock(ProfileRepository.class);
        Mockito.when(profileRepo.findByUserId(1L)).thenReturn(Optional.empty());

        MatchService service = new MatchService(profileRepo);
        Exception e = assertThrows(IllegalArgumentException.class, () -> service.getMatchedProfiles(1L));
        assertEquals("Profile not found", e.getMessage());
    }

    @Test
    void calculateMatchScoreShouldWork() {
        ProfileRepository profileRepo = Mockito.mock(ProfileRepository.class);

        Profile a = new Profile();
        a.setLocation("Bangkok"); a.setLifestyle("Quiet"); a.setBudget(6000.0); a.setPetFriendly(true);

        Profile b = new Profile();
        b.setLocation("Bangkok"); b.setLifestyle("Quiet"); b.setBudget(6100.0); b.setPetFriendly(true);

        MatchService service = new MatchService(profileRepo);
        double score = service.calculateMatchScore(a, b);
        assertEquals(100.0, score); // 30+30+20+20
    }

    @Test
    void filterProfilesShouldReturnFilteredList() {
        ProfileRepository profileRepo = Mockito.mock(ProfileRepository.class);

        Profile p1 = new Profile();
        p1.setLocation("Bangkok"); p1.setLifestyle("Quiet"); p1.setBudget(6000.0); p1.setPetFriendly(true);

        Profile p2 = new Profile();
        p2.setLocation("Bangkok"); p2.setLifestyle("Lively"); p2.setBudget(9000.0); p2.setPetFriendly(false);

        List<Profile> allProfiles = Arrays.asList(p1, p2);
        Mockito.when(profileRepo.findAll()).thenReturn(allProfiles);

        FilterOptions filter = new FilterOptions();
        filter.setLocation("Bangkok");
        filter.setLifestyle("Quiet");
        filter.setMinBudget(5000.0);
        filter.setMaxBudget(7000.0);
        filter.setPetFriendly(true);

        MatchService service = new MatchService(profileRepo);
        List<Profile> filtered = service.filterProfiles(filter);

        assertEquals(1, filtered.size());
        assertEquals(p1, filtered.get(0));
    }
}