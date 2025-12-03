package com.priyansu.project.lovable_clone.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter  //direct shortCut using lombok
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)  //so need to write private in this class (lombok making easy)
public class User {
    Long id;

    String email;

    String passwordHash;
    String avatarUrl;
    Instant createdAt;
    Instant updatedAt;
    Instant deletedAt; //soft Delete


}
