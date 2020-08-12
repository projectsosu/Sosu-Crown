/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.core.config;

import com.fasterxml.jackson.core.JsonParseException;
import com.mongodb.lang.Nullable;
import com.sosu.rest.crown.core.exception.SoSuException;
import com.sosu.rest.crown.core.exception.SoSuSecurityException;
import com.sosu.rest.crown.core.model.ErrorData;
import com.sosu.rest.crown.service.core.MailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
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

    @Autowired
    private MailService mailService;

    @ExceptionHandler
    protected ResponseEntity<Object> handleResponseStatus(ResponseStatusException ex, WebRequest request) {
        return ResponseEntity.badRequest().body(new ErrorData(LocalDateTime.now().toString(), ex.getStatus().value(),
                ex.getMessage(), ex.getReason(), ((ServletWebRequest) request).getRequest().getRequestURI()));
    }

    @ExceptionHandler
    protected ResponseEntity<Object> handleSecurity(SoSuSecurityException ex, WebRequest request) {
        return ResponseEntity.status(ex.getStatus()).body(new ErrorData(LocalDateTime.now().toString(), ex.getStatus().value(),
                ex.getReason(), ex.getCause().getMessage(), ((ServletWebRequest) request).getRequest().getRequestURI()));
    }

    @ExceptionHandler
    protected ResponseEntity<Object> handleSoSuException(SoSuException ex, WebRequest request) {
        return ResponseEntity.status(ex.getStatus()).body(new ErrorData(LocalDateTime.now().toString(), ex.getStatus().value(),
                ex.getReason(), ex.getCause().getMessage(), ((ServletWebRequest) request).getRequest().getRequestURI()));
    }

    @ExceptionHandler
    protected ResponseEntity<Object> handleBadCredential(BadCredentialsException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorData(LocalDateTime.now().toString(), HttpStatus.UNAUTHORIZED.value(),
                ex.getMessage(), "BAD_CREDENTIAL", ((ServletWebRequest) request).getRequest().getRequestURI()));
    }

    @ExceptionHandler
    protected ResponseEntity<Object> handleJsonException(JsonParseException ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.status(status.value()).body(new ErrorData(LocalDateTime.now().toString(), status.value(),
                "Json parse exception: " + ex.getMessage(), "JSON_EXCEPTION", ((ServletWebRequest) request).getRequest().getRequestURI()));
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
        return ResponseEntity.status(status).body(new ErrorData(LocalDateTime.now().toString(), status.value(),
                ex.getMessage(), "NOT_VALID", ((ServletWebRequest) request).getRequest().getRequestURI()));
    }

    @ExceptionHandler
    protected ResponseEntity<Object> handle(Exception ex, WebRequest request) {
        log.error("Unexpected error: {}", ex.getMessage());
        log.error("Unexpected trace: {}", ExceptionUtils.getStackTrace(ex));
        mailService.exceptionMailSender(ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorData(LocalDateTime.now().toString(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Sorry unexpected error occurred :( We sent a mail to our system admins and they will solve this problem as soon as possible.",
                "UNKNOWN_ERR", ((ServletWebRequest) request).getRequest().getRequestURI()));
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("Unexpected IS error: {}", ex.getMessage());
        log.error("Unexpected IS trace: {}", ExceptionUtils.getStackTrace(ex));
        mailService.exceptionMailSender(ex);
        return ResponseEntity.status(status.value()).body(new ErrorData(LocalDateTime.now().toString(), status.value(),
                "Sorry unexpected error occurred :( We sent a mail to our system admins and they will solve this problem as soon as possible.",
                "UNKNOWN_ERR", ((ServletWebRequest) request).getRequest().getRequestURI()));
    }


    private ResponseEntity<Object> validationExceptionMessageCreator(ServletWebRequest request, BindingResult bindingResult) {
        return ResponseEntity.badRequest().body(new ErrorData(LocalDateTime.now().toString(), HttpStatus.BAD_REQUEST.value(),
                bindingResult.getFieldError().getField() + " is not valid", "NOT_VALID",
                request.getRequest().getRequestURI()));
    }

}