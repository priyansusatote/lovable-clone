package com.priyansu.project.lovable_clone.dto.project;

import java.time.Instant;

public record FileNode(
        String path
) {
    @Override
    public String toString() { //to only get path (earlier [fileNode .. this type in Prompt
        return path;
    }
}
