package org.bosf.moondance.repository;

import org.bosf.moondance.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    
    @Query("SELECT c FROM Course c WHERE c.department.id = :departmentId AND c.deletedAt IS NULL ORDER BY c.code")
    List<Course> findByDepartmentId(Long departmentId);
    
    @Query("SELECT c FROM Course c WHERE c.department.school.id = :schoolId AND c.deletedAt IS NULL ORDER BY c.code")
    List<Course> findBySchoolId(Long schoolId);
    
    @Query("SELECT c FROM Course c WHERE c.department.school.id = :schoolId AND c.deletedAt IS NULL " +
           "AND (LOWER(c.code) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(c.title) LIKE LOWER(CONCAT('%', :query, '%')))")
    Page<Course> searchBySchoolId(Long schoolId, String query, Pageable pageable);
    
    @Query("SELECT c FROM Course c WHERE c.id = :id AND c.deletedAt IS NULL")
    Optional<Course> findActiveById(Long id);
    
    @Query("SELECT c FROM Course c WHERE c.code = :code AND c.department.id = :departmentId AND c.deletedAt IS NULL")
    Optional<Course> findByCodeAndDepartmentId(String code, Long departmentId);
}
