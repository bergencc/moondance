package org.bosf.moondance.repository;

import org.bosf.moondance.entity.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    
    @Query("SELECT n FROM Note n WHERE n.courseSession.id = :courseSessionId AND n.deletedAt IS NULL ORDER BY n.createdAt DESC")
    Page<Note> findByCourseSessionId(Long courseSessionId, Pageable pageable);
    
    @Query("SELECT n FROM Note n WHERE n.uploader.id = :uploaderId AND n.deletedAt IS NULL ORDER BY n.createdAt DESC")
    Page<Note> findByUploaderId(Long uploaderId, Pageable pageable);
    
    @Query("SELECT n FROM Note n WHERE n.id = :id AND n.deletedAt IS NULL")
    Optional<Note> findActiveById(Long id);
    
    @Query("SELECT n FROM Note n WHERE n.courseSession.course.department.school.id = :schoolId AND n.deletedAt IS NULL " +
           "AND (LOWER(n.title) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR LOWER(n.description) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR LOWER(n.extractedContent) LIKE LOWER(CONCAT('%', :query, '%')))")
    Page<Note> searchBySchoolId(Long schoolId, String query, Pageable pageable);
    
    @Query("SELECT n FROM Note n WHERE n.courseSession.course.department.school.id = :schoolId AND n.deletedAt IS NULL " +
           "ORDER BY n.downloadCount DESC, n.averageRating DESC")
    Page<Note> findTrendingBySchoolId(Long schoolId, Pageable pageable);
    
    @Query("SELECT n FROM Note n WHERE n.courseSession.course.department.school.id = :schoolId AND n.deletedAt IS NULL " +
           "ORDER BY n.createdAt DESC")
    Page<Note> findRecentBySchoolId(Long schoolId, Pageable pageable);
    
    @Query("SELECT n FROM Note n WHERE n.type = :type AND n.courseSession.course.department.school.id = :schoolId " +
           "AND n.deletedAt IS NULL ORDER BY n.createdAt DESC")
    Page<Note> findByTypeAndSchoolId(Note.NoteType type, Long schoolId, Pageable pageable);
    
    @Query("SELECT n FROM Note n WHERE n.processingStatus = :status")
    List<Note> findByProcessingStatus(Note.ProcessingStatus status);
    
    @Modifying
    @Query("UPDATE Note n SET n.viewCount = n.viewCount + 1 WHERE n.id = :id")
    void incrementViewCount(Long id);
    
    @Modifying
    @Query("UPDATE Note n SET n.downloadCount = n.downloadCount + 1 WHERE n.id = :id")
    void incrementDownloadCount(Long id);
    
    @Query("SELECT COUNT(n) FROM Note n WHERE n.courseSession.id = :courseSessionId AND n.deletedAt IS NULL")
    Long countByCourseSessionId(Long courseSessionId);
    
    @Query("SELECT SUM(n.downloadCount) FROM Note n WHERE n.courseSession.id = :courseSessionId AND n.deletedAt IS NULL")
    Long sumDownloadsByCourseSessionId(Long courseSessionId);
}
