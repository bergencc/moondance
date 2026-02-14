package org.bosf.moondance.repository;

import org.bosf.moondance.entity.CourseSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseSessionRepository extends JpaRepository<CourseSession, Long> {
    
    @Query("SELECT cs FROM CourseSession cs WHERE cs.course.id = :courseId ORDER BY cs.session.year DESC")
    List<CourseSession> findByCourseId(Long courseId);
    
    @Query("SELECT cs FROM CourseSession cs WHERE cs.session.id = :sessionId")
    List<CourseSession> findBySessionId(Long sessionId);
    
    @Query("SELECT cs FROM CourseSession cs WHERE cs.instructor.id = :instructorId")
    List<CourseSession> findByInstructorId(Long instructorId);
    
    @Query("SELECT cs FROM CourseSession cs WHERE cs.course.id = :courseId AND cs.session.id = :sessionId")
    Optional<CourseSession> findByCourseIdAndSessionId(Long courseId, Long sessionId);
    
    @Query("SELECT cs FROM CourseSession cs WHERE cs.course.id = :courseId AND cs.session.id = :sessionId AND cs.instructor.id = :instructorId")
    Optional<CourseSession> findByCourseIdAndSessionIdAndInstructorId(Long courseId, Long sessionId, Long instructorId);
    
    @Query("SELECT cs FROM CourseSession cs JOIN FETCH cs.course c JOIN FETCH cs.session s " +
           "WHERE c.department.school.id = :schoolId ORDER BY s.year DESC, c.code")
    List<CourseSession> findBySchoolId(Long schoolId);
}
