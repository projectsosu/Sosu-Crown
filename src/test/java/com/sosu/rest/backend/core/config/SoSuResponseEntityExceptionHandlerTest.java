package com.sosu.rest.backend.core.config;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.sosu.rest.backend.core.exception.SoSuException;
import com.sosu.rest.backend.core.exception.SoSuSecurityException;
import com.sosu.rest.backend.core.model.ErrorData;
import com.sosu.rest.backend.service.core.MailService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SoSuResponseEntityExceptionHandlerTest {

    @Mock
    private MailService mailService;

    @InjectMocks
    private SoSuResponseEntityExceptionHandler soSuResponseEntityExceptionHandler;

    @Test
    void handleResponseStatus() {
        ResponseEntity<Object> responseEntity = soSuResponseEntityExceptionHandler
                .handleResponseStatus(new ResponseStatusException(HttpStatus.ACCEPTED, ""), getMockRequest());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void handleSecurity() {
        ResponseEntity<Object> responseEntity = soSuResponseEntityExceptionHandler
                .handleSecurity(new SoSuSecurityException(HttpStatus.BAD_GATEWAY, "Example", "ERROR"), getMockRequest());
        assertEquals(HttpStatus.BAD_GATEWAY, responseEntity.getStatusCode());
        ErrorData errorData = (ErrorData) responseEntity.getBody();
        assertEquals("ERROR", Objects.requireNonNull(errorData).getMessage());
        assertEquals("Example", errorData.getError());
    }

    @Test
    void handleSoSuException() {
        ResponseEntity<Object> responseEntity = soSuResponseEntityExceptionHandler
                .handleSoSuException(new SoSuException(HttpStatus.BAD_GATEWAY, "Example", "ERROR"), getMockRequest());
        assertEquals(HttpStatus.BAD_GATEWAY, responseEntity.getStatusCode());
        ErrorData errorData = (ErrorData) responseEntity.getBody();
        assertEquals("ERROR", Objects.requireNonNull(errorData).getMessage());
        assertEquals("Example", errorData.getError());
    }

    @Test
    void handleBadCredential() {
        ResponseEntity<Object> responseEntity = soSuResponseEntityExceptionHandler
                .handleBadCredential(new BadCredentialsException("Example"), getMockRequest());
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        ErrorData errorData = (ErrorData) responseEntity.getBody();
        assertEquals("BAD_CREDENTIAL", Objects.requireNonNull(errorData).getMessage());
        assertEquals("Example", errorData.getError());
    }

    @Test
    void handleJsonException() {
        ResponseEntity<Object> responseEntity = soSuResponseEntityExceptionHandler
                .handleJsonException(new JsonParseException(mock(JsonParser.class), "123456"), mock(Object.class), mock(HttpHeaders.class),
                        HttpStatus.BAD_REQUEST, getMockRequest());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        ErrorData errorData = (ErrorData) responseEntity.getBody();
        assertEquals("JSON_EXCEPTION", Objects.requireNonNull(errorData).getMessage());
        assertEquals("Json parse exception: 123456", errorData.getError());
    }

    @Test
    void handleMethodArgumentNotValid() {
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = mock(FieldError.class);
        when(bindingResult.getFieldError()).thenReturn(fieldError);
        when(fieldError.getField()).thenReturn("Exampleclass.abcd");
        ResponseEntity<Object> responseEntity = soSuResponseEntityExceptionHandler
                .handleMethodArgumentNotValid(new MethodArgumentNotValidException(mock(MethodParameter.class), bindingResult),
                        mock(HttpHeaders.class), HttpStatus.BAD_REQUEST, getMockRequest());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        ErrorData errorData = (ErrorData) responseEntity.getBody();
        assertEquals("NOT_VALID", Objects.requireNonNull(errorData).getMessage());
        assertEquals("Exampleclass.abcd is not valid", errorData.getError());
    }

    @Test
    void handleBindException() {
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = mock(FieldError.class);
        when(bindingResult.getFieldError()).thenReturn(fieldError);
        when(fieldError.getField()).thenReturn("Exampleclass.abcd");
        ResponseEntity<Object> responseEntity = soSuResponseEntityExceptionHandler
                .handleBindException(new BindException(bindingResult), mock(HttpHeaders.class), HttpStatus.BAD_REQUEST, getMockRequest());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        ErrorData errorData = (ErrorData) responseEntity.getBody();
        assertEquals("NOT_VALID", Objects.requireNonNull(errorData).getMessage());
        assertEquals("Exampleclass.abcd is not valid", errorData.getError());
    }

    @Test
    void handleMissingServletRequestParameter() {
        ResponseEntity<Object> responseEntity = soSuResponseEntityExceptionHandler
                .handleMissingServletRequestParameter(new MissingServletRequestParameterException("13", "12"), mock(HttpHeaders.class),
                        HttpStatus.BAD_REQUEST, getMockRequest());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        ErrorData errorData = (ErrorData) responseEntity.getBody();
        assertEquals("NOT_VALID", Objects.requireNonNull(errorData).getMessage());
        assertEquals("Required 12 parameter '13' is not present", errorData.getError());
    }

    @Test
    void handle() {
        ResponseEntity<Object> responseEntity = soSuResponseEntityExceptionHandler.handle(new Exception("Example"), getMockRequest());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        ErrorData errorData = (ErrorData) responseEntity.getBody();
        assertEquals("UNKNOWN_ERR", Objects.requireNonNull(errorData).getMessage());
        assertEquals("Sorry unexpected error occurred :( We sent a mail to our system admins and they will solve this problem as soon as possible.", errorData.getError());
        verify(mailService, times(1)).exceptionMailSender(any());
    }

    @Test
    void handleExceptionInternal() {
        ResponseEntity<Object> responseEntity = soSuResponseEntityExceptionHandler
                .handleExceptionInternal(new Exception(), mock(Object.class), mock(HttpHeaders.class), HttpStatus.INTERNAL_SERVER_ERROR, getMockRequest());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        ErrorData errorData = (ErrorData) responseEntity.getBody();
        assertEquals("UNKNOWN_ERR", Objects.requireNonNull(errorData).getMessage());
        assertEquals("Sorry unexpected error occurred :( We sent a mail to our system admins and they will solve this problem as soon as possible.", errorData.getError());
        verify(mailService, times(1)).exceptionMailSender(any());
    }

    private WebRequest getMockRequest() {
        return new ServletWebRequest(mock(HttpServletRequest.class));
    }

    @AfterEach
    void cleanTests() {
        reset(mailService);
    }
}