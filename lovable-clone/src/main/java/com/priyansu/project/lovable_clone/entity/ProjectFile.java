package com.priyansu.project.lovable_clone.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)

public class ProjectFile {

    Long id;

    Project project; //each file hase to belong with project

    String minioObjectKey;

    User createdBy;

    User updatedBy;

    Instant createdAt;
    Instant updatedAt;
}
