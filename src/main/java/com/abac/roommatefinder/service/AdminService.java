package com.abac.roommatefinder.service;

import com.abac.roommatefinder.entity.Report;
import com.abac.roommatefinder.entity.ReportStatus;
import com.abac.roommatefinder.entity.ReportType;
import com.abac.roommatefinder.entity.User;
import com.abac.roommatefinder.repository.ReportRepository;
import com.abac.roommatefinder.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminService {
    private final UserRepository userRepository;
    private final ReportRepository reportRepository;

    public AdminService(UserRepository userRepository, ReportRepository reportRepository) {
        this.userRepository = userRepository;
        this.reportRepository = reportRepository;
    }

    public void banUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        if (user.isBanned()) {
            throw new IllegalStateException("User is already banned");
        }

        user.setBanned(true);
        userRepository.save(user);
    }

    public void unbanUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        user.setBanned(false);
        userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }
        userRepository.deleteById(userId);
    }

    public Report reportUser(Long reporterId, Long reportedId, String reason) {
        return reportUser(reporterId, reportedId, reason, ReportType.OTHER, null);
    }

    public Report reportUser(Long reporterId, Long reportedId, String reason, ReportType type, String description) {
        // Validation
        if (reporterId.equals(reportedId)) {
            throw new IllegalArgumentException("Users cannot report themselves");
        }

        User reporter = userRepository.findById(reporterId)
                .orElseThrow(() -> new IllegalArgumentException("Reporter not found with ID: " + reporterId));

        User reportedUser = userRepository.findById(reportedId)
                .orElseThrow(() -> new IllegalArgumentException("Reported user not found with ID: " + reportedId));

        // Check for duplicate pending reports
        boolean duplicateExists = reportRepository.existsByReporterUserIdAndReportedUserIdAndStatus(
                reporterId, reportedId, ReportStatus.PENDING);

        if (duplicateExists) {
            throw new IllegalStateException("You have already reported this user");
        }

        Report report = new Report(reportedUser, reporter, reason, type);
        report.setDescription(description);

        return reportRepository.save(report);
    }

    public List<Report> viewAllReports() {
        return reportRepository.findAll();
    }

    public List<Report> viewPendingReports() {
        return reportRepository.findPendingReports();
    }

    public List<Report> viewReportsByStatus(ReportStatus status) {
        return reportRepository.findByStatusOrderByCreatedAtDesc(status);
    }

    public Report resolveReport(Long reportId, Long adminId, ReportStatus newStatus) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new IllegalArgumentException("Report not found with ID: " + reportId));

        if (report.getStatus() != ReportStatus.PENDING) {
            throw new IllegalStateException("Report is already resolved");
        }

        report.setStatus(newStatus);
        report.setResolvedAt(LocalDateTime.now());
        report.setResolvedByUserId(adminId);

        return reportRepository.save(report);
    }

    public Long getReportCountForUser(Long userId) {
        return reportRepository.countTotalReportsForUser(userId);
    }
}
