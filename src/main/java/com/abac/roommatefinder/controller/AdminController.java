package com.abac.roommatefinder.controller;

import com.abac.roommatefinder.dto.ReportRequest;
import com.abac.roommatefinder.entity.Report;
import com.abac.roommatefinder.entity.ReportStatus;
import com.abac.roommatefinder.entity.ReportType;
import com.abac.roommatefinder.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // Ban user
    @PostMapping("/ban/{userId}")
    public ResponseEntity<?> banUser(@PathVariable("userId") Long userId) {
        adminService.banUser(userId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User banned successfully");
        return ResponseEntity.ok(response);
    }

    // Unban user
    @PostMapping("/unban/{userId}")
    public ResponseEntity<?> unbanUser(@PathVariable("userId") Long userId) {
        adminService.unbanUser(userId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User unbanned successfully");
        return ResponseEntity.ok(response);
    }

    // Delete user
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Long userId) {
        adminService.deleteUser(userId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User deleted successfully");
        return ResponseEntity.ok(response);
    }

    // Create report
    @PostMapping("/report")
    public ResponseEntity<?> createReport(@RequestBody ReportRequest request) {
        Report report = adminService.reportUser(
                request.getReporterId(),
                request.getReportedUserId(),
                request.getReason(),
                request.getType() != null ? request.getType() : ReportType.OTHER,
                request.getDescription()
        );
        return ResponseEntity.ok(report);
    }

    // View all reports
    @GetMapping("/reports")
    public ResponseEntity<?> getAllReports() {
        List<Report> reports = adminService.viewAllReports();
        return ResponseEntity.ok(reports);
    }

    // View pending reports
    @GetMapping("/reports/pending")
    public ResponseEntity<?> getPendingReports() {
        List<Report> reports = adminService.viewPendingReports();
        return ResponseEntity.ok(reports);
    }

    // View reports by status
    @GetMapping("/reports/status/{status}")
    public ResponseEntity<?> getReportsByStatus(@PathVariable("status") ReportStatus status) {
        List<Report> reports = adminService.viewReportsByStatus(status);
        return ResponseEntity.ok(reports);
    }

    // Resolve report
    @PostMapping("/reports/{reportId}/resolve")
    public ResponseEntity<?> resolveReport(
            @PathVariable("reportId") Long reportId,
            @RequestParam ReportStatus status,
            @RequestParam Long adminId) {
        Report report = adminService.resolveReport(reportId, adminId, status);
        return ResponseEntity.ok(report);
    }

    // Get report count for user
    @GetMapping("/users/{userId}/report-count")
    public ResponseEntity<?> getUserReportCount(@PathVariable("userId") Long userId) {
        Long count = adminService.getReportCountForUser(userId);
        Map<String, Long> response = new HashMap<>();
        response.put("reportCount", count);
        return ResponseEntity.ok(response);
    }
}
