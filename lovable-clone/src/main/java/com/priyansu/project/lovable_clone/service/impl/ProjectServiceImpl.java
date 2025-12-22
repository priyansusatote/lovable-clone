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
import com.priyansu.project.lovable_clone.service.ProjectService;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
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
    public ProjectResponse getProjectById(Long id) {
        Long userId = authUtil.getCurrentUserId();

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", id.toString()));

        projectMemberRepository.findByProjectIdAndUserId(id, userId)
                .orElseThrow(() -> new ForbiddenException("Not authorized"));


        return projectMapper.toResponse(project);
    }

    @Override
    public ProjectResponse createProject(ProjectRequest request) {
        Long userId = authUtil.getCurrentUserId();

        // 1️⃣ Find the owner of the project (throw error if user does not exist)
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user", userId.toString()));

        // 2️⃣ Create a new Project entity using the request data
        Project project = Project.builder()
                .name(request.name())
                .isPublic(false)
                .build();

        // 3️⃣ Save the newly created project into the database
        Project saved = projectRepository.save(project);

        //Whenever a Project is Created a "ProjectMember" also created
        ProjectMemberId projectMemberId = new ProjectMemberId(project.getId(), owner.getId());
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
    public ProjectResponse updateProject(Long id, ProjectRequest request) { // "request" is dto getting from user
        Long userId = authUtil.getCurrentUserId();
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", id.toString()));

        ProjectMember member =
                projectMemberRepository.findByProjectIdAndUserId(id, userId)
                        .orElseThrow(() -> new ForbiddenException("Not authorized"));

        if (member.getProjectRole() != ProjectRole.OWNER) {
            throw new ForbiddenException("Only owner can update project");
        }
        //dto("request Dto")-> Entity
        project.setName(request.name());


        //save
        Project updated = projectRepository.save(project);

        return projectMapper.toResponse(updated); //Entity (updated) -> dto

    }

    @Override
    public void softDelete(Long id) {
        Long userId = authUtil.getCurrentUserId();

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", id.toString()));

        ProjectMember member =
                projectMemberRepository.findByProjectIdAndUserId(id, userId)
                        .orElseThrow(() -> new ForbiddenException("Not authorized"));

        if (member.getProjectRole() != ProjectRole.OWNER) {
            throw new ForbiddenException("Only owner can Delete project");
        }

        project.setDeletedAt(Instant.now());

        //save
        projectRepository.save(project);
    }
}

