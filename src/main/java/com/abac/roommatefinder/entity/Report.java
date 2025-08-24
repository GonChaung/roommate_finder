package com.abac.roommatefinder.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
@Data
@NoArgsConstructor  // Only keep this one
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_user_id", nullable = false)
    private User reportedUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_user_id", nullable = false)
    private User reporterUser;

    @Column(nullable = false)
    private String reason;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportStatus status = ReportStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportType type = ReportType.OTHER;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime resolvedAt;
    private Long resolvedByUserId;

    // Custom constructors
    public Report(User reportedUser, User reporterUser, String reason) {
        this.reportedUser = reportedUser;
        this.reporterUser = reporterUser;
        this.reason = reason;
        this.status = ReportStatus.PENDING;
        this.type = ReportType.OTHER;
        this.createdAt = LocalDateTime.now();
    }

    public Report(User reportedUser, User reporterUser, String reason, ReportType type) {
        this(reportedUser, reporterUser, reason);
        this.type = type;
    }
}