package com.priyansu.project.lovable_clone.service.impl;

import com.priyansu.project.lovable_clone.dto.project.ProjectRequest;
import com.priyansu.project.lovable_clone.dto.project.ProjectResponse;
import com.priyansu.project.lovable_clone.dto.project.ProjectSummeryResponse;
import com.priyansu.project.lovable_clone.entity.Project;
import com.priyansu.project.lovable_clone.entity.User;
import com.priyansu.project.lovable_clone.mapper.ProjectMapper;
import com.priyansu.project.lovable_clone.repository.ProjectRepository;
import com.priyansu.project.lovable_clone.repository.UserRepository;
import com.priyansu.project.lovable_clone.service.ProjectService;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;



    @Override
    public List<ProjectSummeryResponse> getUserProject(Long userId) {

        var projects = projectRepository.findAllByOwnerId(userId);
        return projectMapper.toProjectResponse(projects);
    }

    @Override
    public ProjectResponse getProjectById(Long id, Long userId) {
        return null;
    }

    @Override
    public ProjectResponse createProject(ProjectRequest request, Long userId) {
        // 1️⃣ Find the owner of the project (throw error if user does not exist)
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2️⃣ Create a new Project entity using the request data
        Project project = Project.builder()
                .name(request.name())
                .isPublic(request.isPublic())
                .owner(owner)
                .build();

        // 3️⃣ Save the newly created project into the database
        Project saved = projectRepository.save(project);

        // 4️⃣ Convert Entity → DTO using MapStruct and return response
        return projectMapper.toResponse(saved);

    }

    @Override
    public ProjectResponse updateProject(Long id, ProjectRequest request, Long userId) {
        return null;
    }

    @Override
    public void softDelete(Long id, Long userId) {

    }
}
