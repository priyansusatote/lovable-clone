package com.priyansu.project.lovable_clone.mapper;

import com.priyansu.project.lovable_clone.dto.project.ProjectResponse;
import com.priyansu.project.lovable_clone.dto.project.ProjectSummeryResponse;
import com.priyansu.project.lovable_clone.entity.Project;
import com.priyansu.project.lovable_clone.enums.ProjectRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {


    ProjectResponse toResponse(Project project);   // converts Project (entity) to ProjectResponse (DTO)

    ProjectSummeryResponse toProjectSummeryResponse(Project project, ProjectRole role);

    List<ProjectSummeryResponse> toProjectResponse(List<Project> projects);
}
