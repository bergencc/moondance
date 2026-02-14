package org.bosf.moondance.repository;

import org.bosf.moondance.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    
    Optional<Tag> findByName(String name);
    
    @Query("SELECT t FROM Tag t WHERE t.name IN :names")
    Set<Tag> findByNameIn(Set<String> names);
    
    @Query("SELECT t FROM Tag t WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Tag> searchByName(String query);
    
    @Query("SELECT t FROM Tag t JOIN t.notes n WHERE n.courseSession.course.department.school.id = :schoolId " +
           "GROUP BY t ORDER BY COUNT(n) DESC")
    List<Tag> findPopularBySchoolId(Long schoolId);
}
