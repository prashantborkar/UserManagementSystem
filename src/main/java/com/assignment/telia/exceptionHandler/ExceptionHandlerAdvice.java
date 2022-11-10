package com.assignment.telia.exceptionHandler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice extends Throwable {

    @ExceptionHandler(ResourceException.class)
    public ResponseEntity handleException(ResourceException e) {

        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }
}
