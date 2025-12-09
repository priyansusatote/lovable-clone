package com.priyansu.project.lovable_clone.mapper;

import com.priyansu.project.lovable_clone.dto.member.MemberResponse;
import com.priyansu.project.lovable_clone.entity.ProjectMember;
import com.priyansu.project.lovable_clone.entity.User;
import com.priyansu.project.lovable_clone.enums.ProjectRole;
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
public class ProjectMemberMapperImpl implements ProjectMemberMapper {

    @Override
    public MemberResponse toProjectMemberResponseFromMember(ProjectMember pm) {
        if ( pm == null ) {
            return null;
        }

        Long id = null;
        String email = null;
        String name = null;
        String avatarUrl = null;
        ProjectRole role = null;
        Instant invitedAt = null;
        Long invitedBy = null;

        id = pmUserId( pm );
        email = pmUserEmail( pm );
        name = pmUserName( pm );
        avatarUrl = pmUserAvatarUrl( pm );
        role = pm.getProjectRole();
        invitedAt = pm.getInvitedAt();
        invitedBy = pmInvitedById( pm );

        MemberResponse memberResponse = new MemberResponse( id, email, name, avatarUrl, role, invitedAt, invitedBy );

        return memberResponse;
    }

    @Override
    public List<MemberResponse> toMemberResponseListFromProjectMember(List<ProjectMember> members) {
        if ( members == null ) {
            return null;
        }

        List<MemberResponse> list = new ArrayList<MemberResponse>( members.size() );
        for ( ProjectMember projectMember : members ) {
            list.add( toProjectMemberResponseFromMember( projectMember ) );
        }

        return list;
    }

    private Long pmUserId(ProjectMember projectMember) {
        User user = projectMember.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getId();
    }

    private String pmUserEmail(ProjectMember projectMember) {
        User user = projectMember.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getEmail();
    }

    private String pmUserName(ProjectMember projectMember) {
        User user = projectMember.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getName();
    }

    private String pmUserAvatarUrl(ProjectMember projectMember) {
        User user = projectMember.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getAvatarUrl();
    }

    private Long pmInvitedById(ProjectMember projectMember) {
        User invitedBy = projectMember.getInvitedBy();
        if ( invitedBy == null ) {
            return null;
        }
        return invitedBy.getId();
    }
}
