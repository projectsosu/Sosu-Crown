package com.sosu.rest.crown.core.config;

import com.sosu.rest.crown.core.model.ErrorData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GeneralAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    protected ResponseEntity<Object> handle(ResponseStatusException ex, WebRequest request) {
        try {
            return ResponseEntity.badRequest().body(new ErrorData(LocalDateTime.now(), ex.getStatus().value(),
                    ex.getCause().getMessage(), ex.getReason(), ((ServletWebRequest) request).getRequest().getRequestURI()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorData(LocalDateTime.now(), ex.getStatus().value(),
                    e.getCause().getMessage(), ex.getReason(), ((ServletWebRequest) request).getRequest().getRequestURI()));
        }
    }
}