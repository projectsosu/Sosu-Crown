package com.sosu.rest.crown.core.config;

import com.sosu.rest.crown.core.exception.SoSuSecurityException;
import com.sosu.rest.crown.core.model.ErrorData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SoSuResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    protected ResponseEntity<Object> handle(ResponseStatusException ex, WebRequest request) {
        return ResponseEntity.badRequest().body(new ErrorData(LocalDateTime.now().toString(), ex.getStatus().value(),
                ex.getCause().getMessage(), ex.getReason(), ((ServletWebRequest) request).getRequest().getRequestURI()));
    }

    @ExceptionHandler
    protected ResponseEntity<Object> handleSecurity(SoSuSecurityException ex, WebRequest request) {
        return ResponseEntity.badRequest().body(new ErrorData(LocalDateTime.now().toString(), ex.getStatus().value(),
                ex.getCause().getMessage(), ex.getReason(), ((ServletWebRequest) request).getRequest().getRequestURI()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        return validationExceptionMessageCreator((ServletWebRequest) request, ex.getBindingResult());
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers,
                                                         HttpStatus status, WebRequest request) {
        return validationExceptionMessageCreator((ServletWebRequest) request, ex.getBindingResult());
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.badRequest().body(new ErrorData(LocalDateTime.now().toString(), status.value(),
                ex.getCause().getMessage(), "NOT_VALID", ((ServletWebRequest) request).getRequest().getRequestURI()));
    }

    private ResponseEntity<Object> validationExceptionMessageCreator(ServletWebRequest request, BindingResult bindingResult) {
        return ResponseEntity.badRequest().body(new ErrorData(LocalDateTime.now().toString(), HttpStatus.BAD_REQUEST.value(),
                bindingResult.getFieldError().getField() + " is not valid", "NOT_VALID",
                request.getRequest().getRequestURI()));
    }

}