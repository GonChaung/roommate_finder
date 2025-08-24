package com.abac.roommatefinder.repository;

import com.abac.roommatefinder.entity.Report;
import com.abac.roommatefinder.entity.ReportStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByReportedUserIdAndStatus(Long reportedUserId, ReportStatus status);
    List<Report> findByReporterUserIdOrderByCreatedAtDesc(Long reporterUserId);
    List<Report> findByStatusOrderByCreatedAtDesc(ReportStatus status);

    @Query("SELECT COUNT(r) FROM Report r WHERE r.reportedUser.id = :userId AND r.status = :status")
    Long countReportsByUserIdAndStatus(@Param("userId") Long userId, @Param("status") ReportStatus status);

    boolean existsByReporterUserIdAndReportedUserIdAndStatus(Long reporterId, Long reportedId, ReportStatus status);

    // Additional useful queries
    @Query("SELECT r FROM Report r WHERE r.status = 'PENDING' ORDER BY r.createdAt ASC")
    List<Report> findPendingReports();

    @Query("SELECT COUNT(r) FROM Report r WHERE r.reportedUser.id = :userId")
    Long countTotalReportsForUser(@Param("userId") Long userId);
}
