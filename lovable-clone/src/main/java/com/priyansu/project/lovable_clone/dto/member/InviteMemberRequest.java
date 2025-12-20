package com.priyansu.project.lovable_clone.dto.member;

import com.priyansu.project.lovable_clone.enums.ProjectRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record InviteMemberRequest(
        @Email @NotBlank String username,
        @NotNull ProjectRole role

) {
}
