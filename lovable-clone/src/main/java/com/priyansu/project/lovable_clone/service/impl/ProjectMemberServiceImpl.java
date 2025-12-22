package com.priyansu.project.lovable_clone.service.impl;

import com.priyansu.project.lovable_clone.dto.member.InviteMemberRequest;
import com.priyansu.project.lovable_clone.dto.member.MemberResponse;
import com.priyansu.project.lovable_clone.dto.member.UpdateMemberRoleRequest;
import com.priyansu.project.lovable_clone.entity.Project;
import com.priyansu.project.lovable_clone.entity.ProjectMember;
import com.priyansu.project.lovable_clone.entity.ProjectMemberId;
import com.priyansu.project.lovable_clone.entity.User;
import com.priyansu.project.lovable_clone.enums.ProjectRole;
import com.priyansu.project.lovable_clone.exception.ForbiddenException;
import com.priyansu.project.lovable_clone.exception.ResourceNotFoundException;
import com.priyansu.project.lovable_clone.mapper.ProjectMemberMapper;
import com.priyansu.project.lovable_clone.repository.ProjectMemberRepository;
import com.priyansu.project.lovable_clone.repository.ProjectRepository;
import com.priyansu.project.lovable_clone.repository.UserRepository;
import com.priyansu.project.lovable_clone.security.AuthUtil;
import com.priyansu.project.lovable_clone.service.ProjectMemberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectMemberServiceImpl implements ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMemberMapper projectMemberMapper;
    private final UserRepository userRepository;
    private final AuthUtil authUtil;

    //Get all members list (only if user requesting this list is the owner)
    @Override
    public List<MemberResponse> getProjectMember(Long projectId) {
        Long userId = authUtil.getCurrentUserId();

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", projectId.toString()));

        ProjectMember member = projectMemberRepository
                .findByProjectIdAndUserId(projectId, userId)
                .orElseThrow(() -> new ForbiddenException("Not authorized"));

        if (member.getProjectRole() != ProjectRole.OWNER) {
            throw new ForbiddenException("Only owner can view members");
        }

        var members = projectMemberRepository.findByProjectId(projectId);  //get all the members stored in the db for this project


        //covert Entity (ProjectMember) to dto (MemberResponse) by Mapper
        return projectMemberMapper.toMemberResponseListFromProjectMember(members);
    }

    @Override
    public MemberResponse inviteMember(Long projectId, InviteMemberRequest request) {
        Long userId = authUtil.getCurrentUserId();

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", projectId.toString()));

        ProjectMember owner = projectMemberRepository
                .findByProjectIdAndUserId(projectId, userId)
                .orElseThrow(() -> new ForbiddenException("Not authorized"));

        if (owner.getProjectRole() != ProjectRole.OWNER) {
            throw new ForbiddenException("Only owner can invite members");
        }

        User userToInvite = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new ResourceNotFoundException("User", request.username()));

        if (userToInvite.getId().equals(userId)) {
            throw new ForbiddenException("Cannot invite yourself");
        }

        ProjectMemberId projectMemberId = new ProjectMemberId(projectId, userToInvite.getId());
        if (projectMemberRepository.existsById(projectMemberId)) {
            throw new ForbiddenException("User is already a member");
        }

        //add new member
        ProjectMember member = ProjectMember.builder()
                .id(projectMemberId)
                .project(project)
                .user(userToInvite)
                .projectRole(request.role())
                .invitedAt(Instant.now())
                .build();

        return projectMemberMapper.toProjectMemberResponseFromMember(projectMemberRepository.save(member));
    }

    @Override
    public MemberResponse updateMemberRole(Long projectId, Long memberId, UpdateMemberRoleRequest request) {
        Long userId = authUtil.getCurrentUserId();

        // 1️⃣ Load the project
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", projectId.toString()));

        // 2️⃣ Check if the logged-in user is the OWNER
        ProjectMember owner = projectMemberRepository
                .findByProjectIdAndUserId(projectId, userId)
                .orElseThrow(() -> new ForbiddenException("Not authorized"));

        if (owner.getProjectRole() != ProjectRole.OWNER) {
            throw new ForbiddenException("Only owner can update roles");
        }

        //owner cannot downgrade their role
        if (memberId.equals(userId)) {
            throw new ForbiddenException("Owner cannot change their own role");
        }


        // 3️⃣ Load the TARGET member (NOT current user)
        ProjectMember member = projectMemberRepository
                .findByProjectIdAndUserId(projectId, memberId)
                .orElseThrow(() -> new ResourceNotFoundException("user not a Member", memberId.toString()));

        // 4️⃣ Update the role
        member.setProjectRole(request.role());

        // 5️⃣ Save and return
        projectMemberRepository.save(member);

        return projectMemberMapper.toProjectMemberResponseFromMember(member);
    }

    @Override
    public void removeProjectMemberRole(Long projectId, Long memberId) {
        Long userId = authUtil.getCurrentUserId();


        // 1️⃣ Find project
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", projectId.toString()));

        // 2️⃣ Only owner can remove members
        ProjectMember owner = projectMemberRepository
                .findByProjectIdAndUserId(projectId, userId)
                .orElseThrow(() -> new ForbiddenException("Not authorized"));

        if (owner.getProjectRole() != ProjectRole.OWNER) {
            throw new ForbiddenException("Only owner can remove members");
        }

        // 3️⃣ Owner cannot remove themselves
        if (memberId.equals(userId)) {
            throw new ForbiddenException("Owner cannot remove themselves");
        }


        // 4️⃣ Check if membership exists
        ProjectMember member = projectMemberRepository
                .findByProjectIdAndUserId(projectId, memberId)
                .orElseThrow(() ->new ResourceNotFoundException("user not a Member", memberId.toString()));

        // 5️⃣ Delete membership
        projectMemberRepository.delete(member);
    }
}
