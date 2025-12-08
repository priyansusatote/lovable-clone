package com.priyansu.project.lovable_clone.mapper;

import com.priyansu.project.lovable_clone.dto.project.ProjectResponse;
import com.priyansu.project.lovable_clone.dto.project.ProjectSummeryResponse;
import com.priyansu.project.lovable_clone.entity.Project;
import com.priyansu.project.lovable_clone.entity.User;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-08T22:43:35+0530",
    comments = "version: 1.6.0, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
@Component
public class ProjectMapperImpl implements ProjectMapper {

    @Override
    public ProjectResponse toResponse(Project project) {
        if ( project == null ) {
            return null;
        }

        Long ownerId = null;
        Long id = null;
        String name = null;
        Boolean isPublic = null;
        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;

        ownerId = projectOwnerId( project );
        id = project.getId();
        name = project.getName();
        isPublic = project.getIsPublic();
        createdAt = map( project.getCreatedAt() );
        updatedAt = map( project.getUpdatedAt() );

        ProjectResponse projectResponse = new ProjectResponse( id, name, isPublic, ownerId, createdAt, updatedAt );

        return projectResponse;
    }

    @Override
    public List<ProjectSummeryResponse> toProjectResponse(List<Project> projects) {
        if ( projects == null ) {
            return null;
        }

        List<ProjectSummeryResponse> list = new ArrayList<ProjectSummeryResponse>( projects.size() );
        for ( Project project : projects ) {
            list.add( projectToProjectSummeryResponse( project ) );
        }

        return list;
    }

    private Long projectOwnerId(Project project) {
        User owner = project.getOwner();
        if ( owner == null ) {
            return null;
        }
        return owner.getId();
    }

    protected ProjectSummeryResponse projectToProjectSummeryResponse(Project project) {
        if ( project == null ) {
            return null;
        }

        Long id = null;
        Long name = null;
        Instant createdAt = null;
        Instant updatedAt = null;

        id = project.getId();
        if ( project.getName() != null ) {
            name = Long.parseLong( project.getName() );
        }
        createdAt = project.getCreatedAt();
        updatedAt = project.getUpdatedAt();

        ProjectSummeryResponse projectSummeryResponse = new ProjectSummeryResponse( id, name, createdAt, updatedAt );

        return projectSummeryResponse;
    }
}
