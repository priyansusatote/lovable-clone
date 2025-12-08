package com.priyansu.project.lovable_clone.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "projects")
public class Project {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    // OWNER RELATION
    @ManyToOne //many project to one user
    @JoinColumn(name = "owner_id", nullable = false)
    User owner;

    Boolean isPublic = false;

    @CreationTimestamp
    Instant createdAt;
    @UpdateTimestamp
    Instant updatedAt;

    Instant deletedAt;

   // @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
   // private List<ProjectMember> members = new ArrayList<>();

  //  @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
  //  private List<ProjectFile> files = new ArrayList<>();

   // @OneToOne(mappedBy = "project", cascade = CascadeType.ALL)
    //private Preview activePreview;
}
