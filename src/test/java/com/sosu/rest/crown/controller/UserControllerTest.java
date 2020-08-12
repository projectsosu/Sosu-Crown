/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.controller;

import com.sosu.rest.crown.controller.impl.UserControllerImpl;
import com.sosu.rest.crown.core.util.JWTUtil;
import com.sosu.rest.crown.model.user.AuthRequest;
import com.sosu.rest.crown.model.user.UserModel;
import com.sosu.rest.crown.model.user.UserRegisterRequest;
import com.sosu.rest.crown.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private JWTUtil jwtUtil;

    @Mock
    private AuthenticationProvider authenticationProvider;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserControllerImpl userController;

    @Test
    void login() {
        when(userService.getUserDetails(anyString())).thenReturn(new UserModel());
        AuthRequest authRequest = mock(AuthRequest.class);
        when(authRequest.getUsername()).thenReturn("");
        ResponseEntity<UserModel> responseEntity = userController.login(authRequest);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(jwtUtil, times(1)).generateToken(any());
        verify(authenticationProvider, times(1)).authenticate(any());
    }

    @Test
    void register() {
        ResponseEntity<Void> responseEntity = userController.register(mock(UserRegisterRequest.class));
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(userService, times(1)).signUpUser(any());
    }

    @Test
    void validate() {
        ResponseEntity<Void> responseEntity = userController.validate("", "");
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(userService, times(1)).validate(any(), any());
    }
}