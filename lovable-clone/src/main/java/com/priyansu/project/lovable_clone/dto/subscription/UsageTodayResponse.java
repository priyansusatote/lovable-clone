package com.priyansu.project.lovable_clone.dto.subscription;

public record UsageTodayResponse(
        int tokenUsed,
        int tokenLimit,
        int previewsRunning,
        int previewsLimit
) {
}
