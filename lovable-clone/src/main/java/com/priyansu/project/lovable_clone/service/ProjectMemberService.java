package com.priyansu.project.lovable_clone.service;

import com.priyansu.project.lovable_clone.dto.member.InviteMemberRequest;
import com.priyansu.project.lovable_clone.dto.member.MemberResponse;
import com.priyansu.project.lovable_clone.entity.ProjectMember;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface ProjectMemberService {
     List<ProjectMember> getProjectMember(Long projectId, Long userId);

     MemberResponse inviteMember(Long projectId, InviteMemberRequest request, Long userId);

     MemberResponse updateMemberRole(Long projectId, Long memberId, Long userId);

     MemberResponse deleteProjectMemberRole(Long projectId, Long memberId, Long userId);
}
