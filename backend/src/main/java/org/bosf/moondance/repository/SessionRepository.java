package org.bosf.moondance.repository;

import org.bosf.moondance.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    
    @Query("SELECT s FROM Session s WHERE s.school.id = :schoolId ORDER BY s.year DESC, s.type")
    List<Session> findBySchoolId(Long schoolId);
    
    @Query("SELECT s FROM Session s WHERE s.school.id = :schoolId AND s.year = :year ORDER BY s.type")
    List<Session> findBySchoolIdAndYear(Long schoolId, Integer year);
    
    @Query("SELECT s FROM Session s WHERE s.school.id = :schoolId AND s.type = :type AND s.year = :year")
    Optional<Session> findBySchoolIdAndTypeAndYear(Long schoolId, Session.SessionType type, Integer year);
}
