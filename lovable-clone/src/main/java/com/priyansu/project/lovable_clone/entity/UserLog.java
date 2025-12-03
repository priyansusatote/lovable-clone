package com.priyansu.project.lovable_clone.entity;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@FieldDefaults(level =  AccessLevel.PRIVATE)
public class UserLog {

    Long id;
    User user;
    Project project;

    String action;

    Integer tokenUsed;
    Integer durationMs;

    String metaData; //store JSON of (modal used, prompt used
    Instant createdAt;
}
