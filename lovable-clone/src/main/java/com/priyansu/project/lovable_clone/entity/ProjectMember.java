package com.priyansu.project.lovable_clone.entity;


import com.priyansu.project.lovable_clone.enums.ProjectRole;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "project_members")
public class ProjectMember {

    @EmbeddedId //Composite primary key  of ProjectMemberId's field(projectId + userId) //JPA reads this as: The primary key("id") of this table is a composite key ,from class ProjectMemberId(which contains this both projectId + userId)) //id Would be made using these projectId + userId
    ProjectMemberId id;

    @ManyToOne
    @MapsId("projectId")  //@MapsId Links the embeddedId field to the relation // connect embedded key to project_id
    Project project;

    @ManyToOne
    @MapsId("userId") // connect embedded key to user_id
    User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    ProjectRole projectRole;

   //removed invitedBy

    Instant invitedAt;
    Instant acceptedAt;


}
