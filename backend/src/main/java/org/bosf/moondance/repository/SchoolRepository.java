package org.bosf.moondance.repository;

import org.bosf.moondance.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {
    
    Optional<School> findByDomain(String domain);
    
    Optional<School> findByInviteCode(String inviteCode);
    
    @Query("SELECT s FROM School s WHERE s.deletedAt IS NULL ORDER BY s.name")
    List<School> findAllActive();
    
    @Query("SELECT s FROM School s WHERE s.id = :id AND s.deletedAt IS NULL")
    Optional<School> findActiveById(Long id);
    
    boolean existsByDomain(String domain);
}
