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
    date = "2026-02-05T15:38:30+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
@Component
public class ProjectMemberMapperImpl implements ProjectMemberMapper {

    @Override
    public MemberResponse toProjectMemberResponseFromMember(ProjectMember pm) {
        if ( pm == null ) {
            return null;
        }

        Long userId = null;
        String username = null;
        String name = null;
        ProjectRole role = null;
        Instant invitedAt = null;

        userId = pmUserId( pm );
        username = pmUserUsername( pm );
        name = pmUserName( pm );
        role = pm.getProjectRole();
        invitedAt = pm.getInvitedAt();

        MemberResponse memberResponse = new MemberResponse( userId, username, name, role, invitedAt );

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

    private String pmUserUsername(ProjectMember projectMember) {
        User user = projectMember.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getUsername();
    }

    private String pmUserName(ProjectMember projectMember) {
        User user = projectMember.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getName();
    }
}
