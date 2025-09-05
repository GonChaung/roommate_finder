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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AdminServiceTest {

    @Test
    void reportUserShouldSaveReport() {
        UserRepository userRepo = Mockito.mock(UserRepository.class);
        ReportRepository reportRepo = Mockito.mock(ReportRepository.class);

        User reporter = new User();
        reporter.setId(1L);
        User reported = new User();
        reported.setId(2L);

        Report expected = new Report(reported, reporter, "Too noisy");

        // Mock userRepo.findById so the service can find users
        Mockito.when(userRepo.findById(1L)).thenReturn(Optional.of(reporter));
        Mockito.when(userRepo.findById(2L)).thenReturn(Optional.of(reported));
        // Mock duplicate check
        Mockito.when(reportRepo.existsByReporterUserIdAndReportedUserIdAndStatus(1L, 2L, ReportStatus.PENDING)).thenReturn(false);
        // Mock save
        Mockito.when(reportRepo.save(Mockito.any(Report.class))).thenReturn(expected);

        AdminService service = new AdminService(userRepo, reportRepo);
        Report result = service.reportUser(1L, 2L, "Too noisy");

        // Compare fields
        assertEquals(expected.getReason(), result.getReason());
        assertEquals(expected.getReporterUser().getId(), result.getReporterUser().getId());
        assertEquals(expected.getReportedUser().getId(), result.getReportedUser().getId());
    }

    @Test
    void viewAllReportsShouldReturnList() {
        UserRepository userRepo = Mockito.mock(UserRepository.class);
        ReportRepository reportRepo = Mockito.mock(ReportRepository.class);

        User reporter1 = new User(); reporter1.setId(1L);
        User reported1 = new User(); reported1.setId(2L);

        User reporter2 = new User(); reporter2.setId(3L);
        User reported2 = new User(); reported2.setId(4L);

        Report report1 = new Report(reported1, reporter1, "Too noisy");
        Report report2 = new Report(reported2, reporter2, "Unpaid bills");

        List<Report> reports = Arrays.asList(report1, report2);
        Mockito.when(reportRepo.findAll()).thenReturn(reports);

        AdminService service = new AdminService(userRepo, reportRepo);
        List<Report> result = service.viewAllReports();

        assertEquals(2, result.size());
        assertEquals("Too noisy", result.get(0).getReason());
        assertEquals("Unpaid bills", result.get(1).getReason());
    }

    @Test
    void deleteUserShouldCallDeleteOnRepo() {
        UserRepository userRepo = Mockito.mock(UserRepository.class);
        ReportRepository reportRepo = Mockito.mock(ReportRepository.class);

        // Mock existsById to return true so no exception is thrown
        Mockito.when(userRepo.existsById(1L)).thenReturn(true);

        AdminService service = new AdminService(userRepo, reportRepo);
        service.deleteUser(1L);

        Mockito.verify(userRepo).deleteById(1L);
    }

    @Test
    void deleteUserShouldThrowIfUserNotFound() {
        UserRepository userRepo = Mockito.mock(UserRepository.class);
        ReportRepository reportRepo = Mockito.mock(ReportRepository.class);

        // Mock existsById to return false
        Mockito.when(userRepo.existsById(99L)).thenReturn(false);

        AdminService service = new AdminService(userRepo, reportRepo);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.deleteUser(99L));
        assertEquals("User not found with ID: 99", ex.getMessage());
    }
}*/
