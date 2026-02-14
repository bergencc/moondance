package org.bosf.moondance.repository;

import org.bosf.moondance.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    
    @Query("SELECT i FROM Instructor i WHERE i.department.id = :departmentId AND i.deletedAt IS NULL ORDER BY i.name")
    List<Instructor> findByDepartmentId(Long departmentId);
    
    @Query("SELECT i FROM Instructor i WHERE i.department.school.id = :schoolId AND i.deletedAt IS NULL ORDER BY i.name")
    List<Instructor> findBySchoolId(Long schoolId);
    
    @Query("SELECT i FROM Instructor i WHERE i.id = :id AND i.deletedAt IS NULL")
    Optional<Instructor> findActiveById(Long id);
    
    @Query("SELECT i FROM Instructor i WHERE i.department.school.id = :schoolId AND i.deletedAt IS NULL " +
           "AND LOWER(i.name) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Instructor> searchBySchoolId(Long schoolId, String query);
}
