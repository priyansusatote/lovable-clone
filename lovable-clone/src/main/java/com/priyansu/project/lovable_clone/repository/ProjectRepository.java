package com.priyansu.project.lovable_clone.repository;

import com.priyansu.project.lovable_clone.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("""
        SELECT p FROM Project p
        WHERE p.owner.id = :userId AND p.deletedAt IS NULL
        ORDER BY p.updatedAt DESC
    """)
    List<Project> findAllByOwnerId(Long userId);
}
