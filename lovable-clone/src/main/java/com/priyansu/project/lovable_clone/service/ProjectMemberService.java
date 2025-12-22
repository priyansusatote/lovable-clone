package com.priyansu.project.lovable_clone.service;

import com.priyansu.project.lovable_clone.dto.member.InviteMemberRequest;
import com.priyansu.project.lovable_clone.dto.member.MemberResponse;
import com.priyansu.project.lovable_clone.dto.member.UpdateMemberRoleRequest;

import java.util.List;

public interface ProjectMemberService {
     List<MemberResponse> getProjectMember(Long projectId);

     MemberResponse inviteMember(Long projectId, InviteMemberRequest request);

     MemberResponse updateMemberRole(Long projectId, Long memberId, UpdateMemberRoleRequest request);

     void removeProjectMemberRole(Long projectId, Long memberId);
}
