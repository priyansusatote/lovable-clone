package com.priyansu.project.lovable_clone.mapper;

import com.priyansu.project.lovable_clone.dto.project.ProjectResponse;
import com.priyansu.project.lovable_clone.dto.project.ProjectSummeryResponse;
import com.priyansu.project.lovable_clone.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    @Mapping(source = "owner.id", target = "ownerId")
    ProjectResponse toResponse(Project project);   // converts Project (entity) to ProjectResponse (DTO)

    // ⭐ Custom mapping: Instant → LocalDateTime
    default LocalDateTime map(Instant instant) {
        if (instant == null) return null;
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
    List<ProjectSummeryResponse> toProjectResponse(List<Project> projects);
}
