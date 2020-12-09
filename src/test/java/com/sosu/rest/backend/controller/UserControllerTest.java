/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.controller;

import com.sosu.rest.backend.controller.impl.UserControllerImpl;
import com.sosu.rest.backend.core.exception.SoSuSecurityException;
import com.sosu.rest.backend.core.util.JWTUtil;
import com.sosu.rest.backend.model.user.AuthRequest;
import com.sosu.rest.backend.model.user.UserBasicDTO;
import com.sosu.rest.backend.model.user.UserFollowRequest;
import com.sosu.rest.backend.model.user.UserModel;
import com.sosu.rest.backend.model.user.UserRegisterRequest;
import com.sosu.rest.backend.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        ResponseEntity<Void> responseEntity = userController.register(mock(UserRegisterRequest.class), mock(Locale.class));
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(userService, times(1)).signUpUser(any(), any());
    }

    @Test
    void validate() {
        ResponseEntity<Void> responseEntity = userController.validate("", "");
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(userService, times(1)).validate(any(), any());
    }

//    @Test
//    void uploadImage() {
//        MultipartFile multipartFile = mock(MultipartFile.class);
//        when(multipartFile.getContentType()).thenReturn("asdadasd");
//        ReflectionTestUtils.setField(userController, "supportedTypes", "JPG;PNG");
//        SoSuException exception = assertThrows(SoSuException.class, () -> userController.uploadFile(multipartFile, "asd"));
//        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, exception.getStatus());
//        assertEquals("Unsupported media type.You can upload JPG, JPEG or PNG images", exception.getReason());
//        assertEquals("UNSUPPORTED_FILE", exception.getCause().getMessage());
//    }
//
//    @Test
//    void uploadImageIO() throws IOException {
//        MultipartFile multipartFile = mock(MultipartFile.class);
//        when(multipartFile.getContentType()).thenReturn("JPG");
//        when(multipartFile.getBytes()).thenThrow(IOException.class);
//        ReflectionTestUtils.setField(userController, "supportedTypes", "JPG;PNG");
//        SoSuException exception = assertThrows(SoSuException.class, () -> userController.uploadFile(multipartFile, "asd"));
//        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, exception.getStatus());
//        assertEquals("UPLOAD_ERROR", exception.getCause().getMessage());
//    }
//
//    @Test
//    void uploadImageSuccess() {
//        MultipartFile multipartFile = mock(MultipartFile.class);
//        when(multipartFile.getContentType()).thenReturn("JPG");
//        ReflectionTestUtils.setField(userController, "supportedTypes", "JPG;PNG");
//        ResponseEntity<Void> responseEntity = userController.uploadFile(multipartFile, "asd");
//        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
//        verify(userService, times(1)).uploadImage(any(), any());
//    }

    @Test
    void getFollowedUsers() {
        UserBasicDTO userBasicDTO = getBasicDto();
        when(userService.getFollowedUsers(any())).thenReturn(Collections.singletonList(userBasicDTO));
        ResponseEntity<List<UserBasicDTO>> responseEntity = userController.getFollowedUsers("username");
        UserBasicDTO response = Objects.requireNonNull(responseEntity.getBody()).get(0);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userBasicDTO.getImage(), response.getImage());
        assertEquals(userBasicDTO.getName(), response.getName());
        assertEquals(userBasicDTO.getUsername(), response.getUsername());
    }

    @Test
    void getFollowerUsers() {
        UserBasicDTO userBasicDTO = getBasicDto();
        when(userService.getFollowerUsers(any())).thenReturn(Collections.singletonList(userBasicDTO));
        ResponseEntity<List<UserBasicDTO>> responseEntity = userController.getFollowerUsers("username");
        UserBasicDTO response = Objects.requireNonNull(responseEntity.getBody()).get(0);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userBasicDTO.getImage(), response.getImage());
        assertEquals(userBasicDTO.getName(), response.getName());
        assertEquals(userBasicDTO.getUsername(), response.getUsername());
    }

    @Test
    void getUserBasic() {
        UserBasicDTO userBasicDTO = getBasicDto();
        when(userService.getUserBasic(any())).thenReturn(userBasicDTO);
        ResponseEntity<UserBasicDTO> responseEntity = userController.getUserBasic("username");
        UserBasicDTO response = Objects.requireNonNull(responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userBasicDTO.getImage(), response.getImage());
        assertEquals(userBasicDTO.getName(), response.getName());
        assertEquals(userBasicDTO.getUsername(), response.getUsername());
    }

    @Test
    void followUser() {
        UserFollowRequest userFollowRequest = new UserFollowRequest();
        userFollowRequest.setFollower("example");
        when(jwtUtil.extractUsername(any())).thenReturn("example");
        ResponseEntity<Void> responseEntity = userController.followUser(userFollowRequest, "asd");
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }


    @Test
    void followUserError() {
        UserFollowRequest userFollowRequest = new UserFollowRequest();
        userFollowRequest.setFollower("example");
        when(jwtUtil.extractUsername(any())).thenReturn("noone");
        SoSuSecurityException exception = assertThrows(SoSuSecurityException.class, () -> userController.followUser(userFollowRequest, "asd"));
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());
        assertEquals("User can not have required permission for this process", exception.getReason());
        assertEquals("UNAUTHORIZED_PROCESS", exception.getCause().getMessage());
    }

    private UserBasicDTO getBasicDto() {
        UserBasicDTO userBasicDTO = new UserBasicDTO();
        userBasicDTO.setName("example");
        userBasicDTO.setImage("exampleImage");
        userBasicDTO.setUsername("exampleUserName");
        return userBasicDTO;
    }
}