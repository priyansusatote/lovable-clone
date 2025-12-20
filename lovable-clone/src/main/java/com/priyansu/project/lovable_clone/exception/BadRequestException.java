package com.priyansu.project.lovable_clone.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


import org.springframework.web.bind.annotation.ResponseStatus;

@RequiredArgsConstructor
@Getter
public class BadRequestException extends RuntimeException {
    private final String message;
}
