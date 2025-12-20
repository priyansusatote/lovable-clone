package com.priyansu.project.lovable_clone.mapper;

import com.priyansu.project.lovable_clone.dto.member.MemberResponse;
import com.priyansu.project.lovable_clone.entity.ProjectMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMemberMapper {
    //to match source and target field
    @Mapping(source = "user.id",        target = "id")          //user.id means = pm.getUser().getId(),  (pm=projectMember)
    @Mapping(source = "user.username",     target = "username")      // Entity.user.email → DTO.email
    @Mapping(source = "user.name",      target = "name")       // Entity.user.name → DTO.name...
    @Mapping(source = "projectRole",    target = "role")
    @Mapping(source = "invitedAt",      target = "invitedAt")
    @Mapping(source = "invitedBy.id", target = "invitedBy")
    MemberResponse toProjectMemberResponseFromMember(ProjectMember pm);

    List<MemberResponse> toMemberResponseListFromProjectMember(List<ProjectMember> members);
}
