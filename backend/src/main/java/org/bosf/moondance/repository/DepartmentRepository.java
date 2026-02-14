package org.bosf.moondance.repository;

import org.bosf.moondance.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    
    @Query("SELECT d FROM Department d WHERE d.school.id = :schoolId AND d.deletedAt IS NULL ORDER BY d.name")
    List<Department> findBySchoolId(Long schoolId);
    
    @Query("SELECT d FROM Department d WHERE d.id = :id AND d.deletedAt IS NULL")
    Optional<Department> findActiveById(Long id);
    
    @Query("SELECT d FROM Department d WHERE d.code = :code AND d.school.id = :schoolId AND d.deletedAt IS NULL")
    Optional<Department> findByCodeAndSchoolId(String code, Long schoolId);
}
