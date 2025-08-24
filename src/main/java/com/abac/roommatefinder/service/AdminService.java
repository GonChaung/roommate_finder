package com.abac.roommatefinder.service;

import com.abac.roommatefinder.entity.Report;
import com.abac.roommatefinder.entity.User;
import com.abac.roommatefinder.repository.ReportRepository;
import com.abac.roommatefinder.repository.UserRepository;
import org.springframework.stereotype.Service;

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
        User u = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        u.setBanned(true);
        userRepository.save(u);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public Report reportUser(Long reporterId, Long reportedId, String reason) {
        return reportRepository.save(new Report(reportedId, reporterId, reason));
    }

    public List<Report> viewReportedUsers() {
        return reportRepository.findAll();
    }
}
