package com.sosu.rest.crown.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ErrorData {

    private LocalDateTime timestamp;

    private int status;

    private String error;

    private String message;

    private String path;

}
