package com.priyansu.project.lovable_clone.mapper;

import com.priyansu.project.lovable_clone.dto.auth.UserProfileResponse;
import com.priyansu.project.lovable_clone.dto.project.ProjectResponse;
import com.priyansu.project.lovable_clone.dto.project.ProjectSummeryResponse;
import com.priyansu.project.lovable_clone.entity.Project;
import com.priyansu.project.lovable_clone.entity.User;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-09T18:33:44+0530",
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
        UserProfileResponse owner = null;
        Instant createdAt = null;
        Instant updatedAt = null;

        ownerId = projectOwnerId( project );
        id = project.getId();
        name = project.getName();
        isPublic = project.getIsPublic();
        owner = userToUserProfileResponse( project.getOwner() );
        createdAt = project.getCreatedAt();
        updatedAt = project.getUpdatedAt();

        ProjectResponse projectResponse = new ProjectResponse( id, name, isPublic, ownerId, owner, createdAt, updatedAt );

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

    protected UserProfileResponse userToUserProfileResponse(User user) {
        if ( user == null ) {
            return null;
        }

        Long id = null;
        String email = null;
        String name = null;
        String avatarUrl = null;

        id = user.getId();
        email = user.getEmail();
        name = user.getName();
        avatarUrl = user.getAvatarUrl();

        UserProfileResponse userProfileResponse = new UserProfileResponse( id, email, name, avatarUrl );

        return userProfileResponse;
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
