package com.sosu.rest.crown.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class SoSuException extends ResponseStatusException {

    public SoSuException(HttpStatus status, String message, String error) {
        super(status, message, new Exception(error));
    }

}
