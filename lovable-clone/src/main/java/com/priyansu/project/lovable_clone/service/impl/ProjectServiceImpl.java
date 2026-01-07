package com.priyansu.project.lovable_clone.service.impl;

import com.priyansu.project.lovable_clone.dto.project.ProjectRequest;
import com.priyansu.project.lovable_clone.dto.project.ProjectResponse;
import com.priyansu.project.lovable_clone.dto.project.ProjectSummeryResponse;
import com.priyansu.project.lovable_clone.entity.Project;
import com.priyansu.project.lovable_clone.entity.ProjectMember;
import com.priyansu.project.lovable_clone.entity.ProjectMemberId;
import com.priyansu.project.lovable_clone.entity.User;
import com.priyansu.project.lovable_clone.enums.ProjectRole;
import com.priyansu.project.lovable_clone.exception.ForbiddenException;
import com.priyansu.project.lovable_clone.exception.ResourceNotFoundException;
import com.priyansu.project.lovable_clone.mapper.ProjectMapper;
import com.priyansu.project.lovable_clone.repository.ProjectMemberRepository;
import com.priyansu.project.lovable_clone.repository.ProjectRepository;
import com.priyansu.project.lovable_clone.repository.UserRepository;
import com.priyansu.project.lovable_clone.security.AuthUtil;
import com.priyansu.project.lovable_clone.security.SecurityExpression;
import com.priyansu.project.lovable_clone.service.ProjectService;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;
    private final ProjectMemberRepository projectMemberRepository;
    private final AuthUtil authUtil;


    @Override
    public List<ProjectSummeryResponse> getUserProject() {
        Long userId = authUtil.getCurrentUserId();

        var projects = projectRepository.findAllAccessibleByUser(userId);
        return projectMapper.toProjectResponse(projects);
    }

    @Override
    @PreAuthorize("@securityExpression.canViewProject(#projectId)") //first S->s should be always lower even Bean/className is not
    public ProjectResponse getProjectById(Long projectId) {
        Long userId = authUtil.getCurrentUserId();

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", projectId.toString()));

/*       projectMemberRepository.findByProjectIdAndUserId(id, userId)
               .orElseThrow(() -> new ForbiddenException("Not authorized")); */   //service level Authorization check removed already done using method security @PreAuthrize


        return projectMapper.toResponse(project);
    }

    @Override
    public ProjectResponse createProject(ProjectRequest request) {
        Long userId = authUtil.getCurrentUserId();

        // 1️⃣  the owner of the project (this is only for setting projectMember.user(owner) //Use getReferenceById() when you only need the entity to form a relationship, not its data.
        User owner = userRepository.getReferenceById(userId);  //reference :It gives you a lightweight reference to an existing User so you can link it to another entity, without loading the user from the database.


        // 2️⃣ Create a new Project entity using the request data
        Project project = Project.builder()
                .name(request.name())
                .isPublic(false)
                .build();

        // 3️⃣ Save the newly created project into the database
        Project saved = projectRepository.save(project);

        //Whenever a Project is Created a "ProjectMember" also created
        ProjectMemberId projectMemberId = new ProjectMemberId(project.getId(), userId);
        ProjectMember projectMember = ProjectMember.builder()
                .id(projectMemberId)
                .projectRole(ProjectRole.OWNER)
                .user(owner)
                .acceptedAt(Instant.now())
                .invitedAt(Instant.now())
                .project(project)
                .build();
        projectMemberRepository.save(projectMember);

        // 4️⃣ Convert Entity → DTO using MapStruct and return response
        return projectMapper.toResponse(saved);

    }

    @Override
    @PreAuthorize("@securityExpression.canEditProject(#projectId)")
    public ProjectResponse updateProject(Long projectId, ProjectRequest request) { // "request" is dto getting from user
        Long userId = authUtil.getCurrentUserId();
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", projectId.toString()));


        //dto("request Dto")-> Entity
        project.setName(request.name());


        //save
        Project updated = projectRepository.save(project);

        return projectMapper.toResponse(updated); //Entity (updated) -> dto

    }

    @Override
    @PreAuthorize("@securityExpression.canDeleteProject(#projectId)")
    public void softDelete(Long projectId) {
        Long userId = authUtil.getCurrentUserId();

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", projectId.toString()));


        project.setDeletedAt(Instant.now());

        //save
        projectRepository.save(project);
    }
}

