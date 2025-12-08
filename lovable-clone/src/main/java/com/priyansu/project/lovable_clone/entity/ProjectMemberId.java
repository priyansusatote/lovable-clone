package com.priyansu.project.lovable_clone.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)

@NoArgsConstructor
@AllArgsConstructor
public class ProjectMemberId {

    Long projectId;
    Long userId;
}
