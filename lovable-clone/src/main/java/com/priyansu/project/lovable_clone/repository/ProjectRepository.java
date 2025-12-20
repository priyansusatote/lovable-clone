package com.priyansu.project.lovable_clone.repository;

import com.priyansu.project.lovable_clone.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("""
    SELECT DISTINCT p FROM Project p
    JOIN ProjectMember pm ON pm.project.id = p.id
    WHERE pm.user.id = :userId
      AND p.deletedAt IS NULL
""")
    List<Project> findAllAccessibleByUser(@Param("userId") Long userId); //@Param is used when you want to bind method parameters to named query parameters.
}
