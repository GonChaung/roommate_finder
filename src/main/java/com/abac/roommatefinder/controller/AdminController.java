package com.abac.roommatefinder.controller;

import com.abac.roommatefinder.entity.Report;
import com.abac.roommatefinder.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // banUser(userId)
    @PostMapping("/ban/{userId}")
    public ResponseEntity<?> ban(@PathVariable Long userId) {
        adminService.banUser(userId);
        return ResponseEntity.ok().build();
    }

    // deleteUser(userId)
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> delete(@PathVariable Long userId) {
        adminService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    // report user (helper to populate reports)
    @PostMapping("/report")
    public ResponseEntity<?> report(@RequestBody Map<String, String> body) {
        Long reporterId = Long.valueOf(body.get("reporterId"));
        Long reportedId = Long.valueOf(body.get("reportedId"));
        String reason = body.getOrDefault("reason", "unspecified");
        Report r = adminService.reportUser(reporterId, reportedId, reason);
        return ResponseEntity.ok(r);
    }

    // viewReportedUsers()
    @GetMapping("/reported")
    public ResponseEntity<?> reported() {
        List<Report> list = adminService.viewReportedUsers();
        return ResponseEntity.ok(list);
    }
}
