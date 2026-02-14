package org.bosf.moondance.repository;

import org.bosf.moondance.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    
    @Query("SELECT r FROM Report r WHERE r.status = :status ORDER BY r.createdAt DESC")
    Page<Report> findByStatus(Report.ReportStatus status, Pageable pageable);
    
    @Query("SELECT r FROM Report r WHERE r.note.id = :noteId ORDER BY r.createdAt DESC")
    List<Report> findByNoteId(Long noteId);
    
    @Query("SELECT r FROM Report r WHERE r.reporter.id = :reporterId ORDER BY r.createdAt DESC")
    List<Report> findByReporterId(Long reporterId);
    
    @Query("SELECT r FROM Report r WHERE r.note.courseSession.course.department.school.id = :schoolId " +
           "AND r.status = :status ORDER BY r.createdAt DESC")
    Page<Report> findBySchoolIdAndStatus(Long schoolId, Report.ReportStatus status, Pageable pageable);
    
    boolean existsByNoteIdAndReporterId(Long noteId, Long reporterId);
}
