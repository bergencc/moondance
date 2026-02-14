package org.bosf.moondance.repository;

import org.bosf.moondance.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    
    Optional<Vote> findByNoteIdAndUserId(Long noteId, Long userId);
    
    @Query("SELECT COUNT(v) FROM Vote v WHERE v.note.id = :noteId AND v.value > 0")
    Long countPositiveByNoteId(Long noteId);
    
    @Query("SELECT COUNT(v) FROM Vote v WHERE v.note.id = :noteId AND v.value < 0")
    Long countNegativeByNoteId(Long noteId);
    
    @Query("SELECT AVG(v.rating) FROM Vote v WHERE v.note.id = :noteId AND v.rating IS NOT NULL")
    Double averageRatingByNoteId(Long noteId);
    
    @Query("SELECT COUNT(v) FROM Vote v WHERE v.note.id = :noteId AND v.rating IS NOT NULL")
    Long countRatingsByNoteId(Long noteId);
    
    boolean existsByNoteIdAndUserId(Long noteId, Long userId);
}
