package com.priyansu.project.lovable_clone.repository;

import com.priyansu.project.lovable_clone.entity.ProjectMember;
import com.priyansu.project.lovable_clone.entity.ProjectMemberId;
import com.priyansu.project.lovable_clone.enums.ProjectRole;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, ProjectMemberId> {

    List<ProjectMember> findByProjectId(Long projectId);

    Optional<ProjectMember> findByProjectIdAndUserId(Long projectId, Long userId);

    void deleteById(ProjectMemberId id);

    @Query("""
       SELECT pm.projectRole 
       FROM ProjectMember pm 
       WHERE pm.id.projectId = :pId 
         AND pm.id.userId = :uId
       """)
    Optional<ProjectRole> findRoleByProjectIdAndUserId(
            @Param("pId") Long projectId,          //@param used to pass/match field name in Query( :projectId or :pId)
            @Param("uId") Long userId
    );
}
