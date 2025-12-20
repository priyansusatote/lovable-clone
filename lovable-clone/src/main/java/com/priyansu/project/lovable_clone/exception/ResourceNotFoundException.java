package com.priyansu.project.lovable_clone.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@RequiredArgsConstructor

public class ResourceNotFoundException extends RuntimeException {
   private final String resourceName;
   private final String resourceId;
}