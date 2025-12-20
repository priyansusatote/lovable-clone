package com.priyansu.project.lovable_clone.dto.member;



import com.priyansu.project.lovable_clone.enums.ProjectRole;

import java.time.Instant;

public record MemberResponse(
        Long id,
        String username,
        String name,
        String avatarUrl,
        ProjectRole role,
        Instant invitedAt,
        Long invitedBy
) {
}
