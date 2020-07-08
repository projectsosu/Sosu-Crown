package com.sosu.rest.crown.core.exception;

import org.springframework.http.HttpStatus;

public class SoSuSecurityException extends SoSuException {

    public SoSuSecurityException(HttpStatus status, String message, String error) {
        super(status, message, error);
    }

}
