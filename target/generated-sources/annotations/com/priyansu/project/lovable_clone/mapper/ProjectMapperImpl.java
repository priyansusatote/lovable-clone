package com.priyansu.project.lovable_clone.mapper;

import com.priyansu.project.lovable_clone.dto.auth.UserProfileResponse;
import com.priyansu.project.lovable_clone.dto.project.ProjectResponse;
import com.priyansu.project.lovable_clone.dto.project.ProjectSummeryResponse;
import com.priyansu.project.lovable_clone.entity.Project;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-09T17:53:35+0530",
    comments = "version: 1.6.0, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
@Component
public class ProjectMapperImpl implements ProjectMapper {

    @Override
    public ProjectResponse toResponse(Project project) {
        if ( project == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        Boolean isPublic = null;
        Instant createdAt = null;
        Instant updatedAt = null;

        id = project.getId();
        name = project.getName();
        isPublic = project.getIsPublic();
        createdAt = project.getCreatedAt();
        updatedAt = project.getUpdatedAt();

        UserProfileResponse owner = null;

        ProjectResponse projectResponse = new ProjectResponse( id, name, isPublic, owner, createdAt, updatedAt );

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

    protected ProjectSummeryResponse projectToProjectSummeryResponse(Project project) {
        if ( project == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        Instant createdAt = null;
        Instant updatedAt = null;

        id = project.getId();
        name = project.getName();
        createdAt = project.getCreatedAt();
        updatedAt = project.getUpdatedAt();

        ProjectSummeryResponse projectSummeryResponse = new ProjectSummeryResponse( id, name, createdAt, updatedAt );

        return projectSummeryResponse;
    }
}
