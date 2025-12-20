package com.priyansu.project.lovable_clone.service.impl;

import com.priyansu.project.lovable_clone.dto.member.InviteMemberRequest;
import com.priyansu.project.lovable_clone.dto.member.MemberResponse;
import com.priyansu.project.lovable_clone.dto.member.UpdateMemberRoleRequest;
import com.priyansu.project.lovable_clone.entity.Project;
import com.priyansu.project.lovable_clone.entity.ProjectMember;
import com.priyansu.project.lovable_clone.entity.ProjectMemberId;
import com.priyansu.project.lovable_clone.entity.User;
import com.priyansu.project.lovable_clone.mapper.ProjectMemberMapper;
import com.priyansu.project.lovable_clone.repository.ProjectMemberRepository;
import com.priyansu.project.lovable_clone.repository.ProjectRepository;
import com.priyansu.project.lovable_clone.repository.UserRepository;
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
    private  final UserRepository userRepository;


    //Get all members list (only if user requesting this list is the owner)
    @Override
    public List<MemberResponse> getProjectMember(Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));




        var members = projectMemberRepository.findByProjectId(projectId);  //get all the members stored in the db for this project


        //covert Entity (ProjectMember) to dto (MemberResponse) by Mapper
        return projectMemberMapper.toMemberResponseListFromProjectMember(members);
    }

    @Override
    public MemberResponse inviteMember(Long projectId, InviteMemberRequest request, Long userId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        // Only owner can invite
        //pending(Authorization)

        User userToInvite = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new RuntimeException("User not found with email"));

        if(userToInvite.getId().equals(userId)){
            throw new RuntimeException("Cannot Invite yourSelf");
        }

        ProjectMemberId projectMemberId = new ProjectMemberId(projectId, userToInvite.getId());
        if(projectMemberRepository.existsById(projectMemberId)){
            throw new RuntimeException("Already a Member, can not invite once again");
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
    public MemberResponse updateMemberRole(Long projectId, Long memberId, UpdateMemberRoleRequest request, Long userId) {
        // 1️⃣ Load the project
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        // 2️⃣ Check if the logged-in user is the OWNER
      //pending

        // 3️⃣ Load the TARGET member (NOT current user)
        ProjectMember member = projectMemberRepository
                .findByProjectIdAndUserId(projectId, memberId)
                .orElseThrow(() -> new RuntimeException("User not a member"));

        // 4️⃣ Update the role
        member.setProjectRole(request.role());

        // 5️⃣ Save and return
        projectMemberRepository.save(member);

        return projectMemberMapper.toProjectMemberResponseFromMember(member);
    }

    @Override
    public void removeProjectMemberRole(Long projectId, Long memberId, Long userId) {

        // 1️⃣ Find project
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        // 2️⃣ Only owner can remove members
     //pending

        // 3️⃣ Owner cannot remove themselves


        // 4️⃣ Check if membership exists
        ProjectMember member = projectMemberRepository
                .findByProjectIdAndUserId(projectId, memberId)
                .orElseThrow(() -> new RuntimeException("User is not a member"));

        // 5️⃣ Delete membership
        projectMemberRepository.delete(member);
    }
}
