/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.core.exception;

import org.springframework.http.HttpStatus;

public class SoSuSecurityException extends SoSuException {

    public SoSuSecurityException(HttpStatus status, String message, String error) {
        super(status, message, error);
    }

}
