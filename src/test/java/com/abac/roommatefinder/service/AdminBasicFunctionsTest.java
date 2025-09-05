/*
package com.abac.roommatefinder.service;

import com.abac.roommatefinder.entity.Report;
import com.abac.roommatefinder.entity.ReportStatus;
import com.abac.roommatefinder.entity.User;
import com.abac.roommatefinder.repository.ReportRepository;
import com.abac.roommatefinder.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

*/
/**
 * Student-level tests (simple & focused) for Admin functions:
 *  - banUser(userId)
 *  - deleteUser(userId)
 *  - viewAllReports()  (acts as viewReportedUsers)
 *
 * Each test sets up only what is needed, uses clear names,
 * and verifies basic expected behavior or exception.
 *//*

class AdminBasicFunctionsTest {

    // ---------------- banUser tests ----------------

    @Test
    void banUser_shouldSetBannedTrue_whenUserExistsAndNotYetBanned() {
        UserRepository userRepo = Mockito.mock(UserRepository.class);
        ReportRepository reportRepo = Mockito.mock(ReportRepository.class);

        User u = new User();
        u.setId(1L);
        u.setBanned(false);

        Mockito.when(userRepo.findById(1L)).thenReturn(Optional.of(u));
        Mockito.when(userRepo.save(u)).thenReturn(u);

        AdminService service = new AdminService(userRepo, reportRepo);
        service.banUser(1L);

        assertTrue(u.isBanned(), "User should now be marked banned");
        Mockito.verify(userRepo).save(u);
    }

    @Test
    void banUser_shouldThrow_whenUserNotFound() {
        UserRepository userRepo = Mockito.mock(UserRepository.class);
        ReportRepository reportRepo = Mockito.mock(ReportRepository.class);

        Mockito.when(userRepo.findById(99L)).thenReturn(Optional.empty());

        AdminService service = new AdminService(userRepo, reportRepo);
        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> service.banUser(99L));
        assertEquals("User not found with ID: 99", ex.getMessage());
    }

    @Test
    void banUser_shouldThrow_whenUserAlreadyBanned() {
        UserRepository userRepo = Mockito.mock(UserRepository.class);
        ReportRepository reportRepo = Mockito.mock(ReportRepository.class);

        User u = new User();
        u.setId(5L);
        u.setBanned(true);

        Mockito.when(userRepo.findById(5L)).thenReturn(Optional.of(u));

        AdminService service = new AdminService(userRepo, reportRepo);
        IllegalStateException ex =
                assertThrows(IllegalStateException.class, () -> service.banUser(5L));
        assertEquals("User is already banned", ex.getMessage());
    }

    // ---------------- deleteUser tests ----------------

    @Test
    void deleteUser_shouldCallRepositoryDelete_whenUserExists() {
        UserRepository userRepo = Mockito.mock(UserRepository.class);
        ReportRepository reportRepo = Mockito.mock(ReportRepository.class);

        Mockito.when(userRepo.existsById(10L)).thenReturn(true);

        AdminService service = new AdminService(userRepo, reportRepo);
        service.deleteUser(10L);

        Mockito.verify(userRepo).deleteById(10L);
    }

    @Test
    void deleteUser_shouldThrow_whenUserDoesNotExist() {
        UserRepository userRepo = Mockito.mock(UserRepository.class);
        ReportRepository reportRepo = Mockito.mock(ReportRepository.class);

        Mockito.when(userRepo.existsById(123L)).thenReturn(false);

        AdminService service = new AdminService(userRepo, reportRepo);
        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> service.deleteUser(123L));
        assertEquals("User not found with ID: 123", ex.getMessage());
    }

    // ---------------- viewReportedUsers (viewAllReports) tests ----------------

    @Test
    void viewAllReports_shouldReturnAllReports_whenReportsExist() {
        UserRepository userRepo = Mockito.mock(UserRepository.class);
        ReportRepository reportRepo = Mockito.mock(ReportRepository.class);

        Report r1 = new Report();
        r1.setReason("Noise");
        r1.setStatus(ReportStatus.PENDING);

        Report r2 = new Report();
        r2.setReason("Spam");
        r2.setStatus(ReportStatus.RESOLVED);

        Mockito.when(reportRepo.findAll()).thenReturn(Arrays.asList(r1, r2));

        AdminService service = new AdminService(userRepo, reportRepo);
        List<Report> list = service.viewAllReports();

        assertEquals(2, list.size());
        assertEquals("Noise", list.get(0).getReason());
        assertEquals("Spam", list.get(1).getReason());
    }

    @Test
    void viewAllReports_shouldReturnEmptyList_whenNoReports() {
        UserRepository userRepo = Mockito.mock(UserRepository.class);
        ReportRepository reportRepo = Mockito.mock(ReportRepository.class);

        Mockito.when(reportRepo.findAll()).thenReturn(Collections.emptyList());

        AdminService service = new AdminService(userRepo, reportRepo);
        List<Report> list = service.viewAllReports();

        assertNotNull(list);
        assertTrue(list.isEmpty());
    }
}*/
