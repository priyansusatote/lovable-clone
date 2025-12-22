package com.priyansu.project.lovable_clone.repository;

import com.priyansu.project.lovable_clone.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    //EXISTS(written here) explained:“Does there exist any row in ProjectMember where this user is a member of this project?”
    //SELECT 1 is just a dummy value. The database ignores it. SELECT 1 = “I don’t want data, just confirmation.” , Why not SELECT pm or SELECT *? : The database does not return data ,It only checks: “Is there at least one matching row?”
    @Query("""
              SELECT p FROM Project p
              WHERE p.deletedAt IS NULL
              AND EXISTS (  
                     SELECT 1 FROM ProjectMember pm   
                     WHERE pm.id.userId = :userId
                     AND pm.id.projectId = p.id
               )
               ORDER BY p.updatedAt DESC
            """)
    List<Project> findAllAccessibleByUser(@Param("userId") Long userId); //@Param is used when you want to bind method parameters to named query parameters.
}
