/**
 * @author : Oguz Kahraman
 * @since : 13.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.service.user;

import com.sosu.rest.crown.core.exception.SoSuException;
import com.sosu.rest.crown.core.service.ImageUploader;
import com.sosu.rest.crown.entity.mongo.Security;
import com.sosu.rest.crown.entity.mongo.User;
import com.sosu.rest.crown.entity.postgres.UserFollow;
import com.sosu.rest.crown.mapper.UserMapper;
import com.sosu.rest.crown.model.service.FFCountModel;
import com.sosu.rest.crown.model.user.UserBasicDTO;
import com.sosu.rest.crown.model.user.UserFollowRequest;
import com.sosu.rest.crown.model.user.UserModel;
import com.sosu.rest.crown.model.user.UserRegisterRequest;
import com.sosu.rest.crown.repo.mongo.SecurityRepository;
import com.sosu.rest.crown.repo.mongo.UserRepository;
import com.sosu.rest.crown.repo.postgres.UserFollowRepository;
import com.sosu.rest.crown.service.core.MailService;
import com.sosu.rest.crown.service.user.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityRepository securityRepository;

    @Mock
    private UserFollowRepository userFollowRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private MailService mailService;

    @Mock
    private ImageUploader imageUploader;

    @InjectMocks
    private UserServiceImpl userService;


    @Test
    void getUserDetails() {
        when(userRepository.findByUsernameOrEmail(any(), any())).thenReturn(mock(User.class));
        when(userMapper.mapEntityToModel(any(User.class))).thenReturn(new UserModel());
        UserModel userModel = userService.getUserDetails("123");
        assertNotNull(userModel);
    }

    @Test
    void getUserDetailsNull() {
        when(userRepository.findByUsernameOrEmail(any(), any())).thenReturn(null);
        SoSuException exception = assertThrows(SoSuException.class, () -> userService.getUserDetails("123"));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("User name can not find", exception.getReason());
        assertEquals("USR_NOT_FOUND", exception.getCause().getMessage());
    }

    @Test
    void signUpUserFoundUserName() {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setUsername("123");
        when(userRepository.findByUsername("123")).thenReturn(new User());
        SoSuException exception = assertThrows(SoSuException.class, () -> userService.signUpUser(userRegisterRequest, mock(Locale.class)));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("User already signed up", exception.getReason());
        assertEquals("USER_FOUND", exception.getCause().getMessage());
    }

    @Test
    void signUpUserFoundEmail() {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("123");
        userRegisterRequest.setUsername("123");
        when(userRepository.findByEmail("123")).thenReturn(new User());
        SoSuException exception = assertThrows(SoSuException.class, () -> userService.signUpUser(userRegisterRequest, mock(Locale.class)));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("User already signed up", exception.getReason());
        assertEquals("EMAIL_FOUND", exception.getCause().getMessage());
    }

    @Test
    void signUpUser() {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("123");
        userRegisterRequest.setUsername("123");
        User user = new User();
        user.setId("");
        user.setBirthDate(LocalDate.now());
        user.setEmail("");
        user.setName("");
        user.setPassword("");
        user.setUsername("");
        user.setImage("");
        user.setValidated(false);
        when(userMapper.registerRequestToModel(any())).thenReturn(user);
        when(userRepository.findByEmail(any())).thenReturn(null);
        userService.signUpUser(userRegisterRequest, mock(Locale.class));
        verify(userRepository, times(1)).save(any());
        verify(securityRepository, times(1)).save(any());
        verify(mailService, times(1)).sendRegisterMail(any(), any(), any());
    }

    @Test
    void validateNotFound() {
        SoSuException exception = assertThrows(SoSuException.class, () -> userService.validate("asd", "asd"));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("User not found", exception.getReason());
        assertEquals("USER_NOT_FOUND", exception.getCause().getMessage());
    }

    @Test
    void validateValidated() {
        User user = new User();
        user.setValidated(true);
        when(userRepository.findByUsernameOrEmail("asd", "asd")).thenReturn(user);
        SoSuException exception = assertThrows(SoSuException.class, () -> userService.validate("ASD", "asd"));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("User already validated", exception.getReason());
        assertEquals("ALREADY_VALIDATED", exception.getCause().getMessage());
    }

    @Test
    void validateValidationError() {
        when(userRepository.findByUsernameOrEmail("asd", "asd")).thenReturn(new User());
        SoSuException exception = assertThrows(SoSuException.class, () -> userService.validate("ASD", "asd"));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Fields not valid", exception.getReason());
        assertEquals("USR_VALIDATION_ERROR", exception.getCause().getMessage());
    }

    @Test
    void validateValidationOutOfDate() {
        Security security = new Security();
        security.setId("");
        security.setUsername("");
        security.setToken("");
        security.setTokenDate(LocalDateTime.now().minusMonths(1));
        security.setTtl(0L);
        when(userRepository.findByUsernameOrEmail("asd", "asd")).thenReturn(new User());
        when(securityRepository.findByUsernameAndToken(any(), any())).thenReturn(security);
        SoSuException exception = assertThrows(SoSuException.class, () -> userService.validate("ASD", "asd"));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Token out of date", exception.getReason());
        assertEquals("TOKEN_OUT_OF_DATE", exception.getCause().getMessage());
    }

    @Test
    void validateValidation() {
        Security security = new Security();
        security.setId("");
        security.setUsername("");
        security.setToken("");
        security.setTokenDate(LocalDateTime.now());
        security.setTtl(0L);
        when(userRepository.findByUsernameOrEmail("asd", "asd")).thenReturn(new User());
        when(securityRepository.findByUsernameAndToken(any(), any())).thenReturn(security);
        userService.validate("ASD", "asd");
        verify(userRepository, times(1)).save(any());
        verify(securityRepository, times(1)).delete(any());
    }

    @Test
    void uploadImage() {
        when(userRepository.findByUsernameOrEmail("123", "123")).thenReturn(new User());
        userService.uploadImage("12".getBytes(), "123");
        verify(userRepository, times(1)).save(any());
        verify(imageUploader, times(1)).uploadProfileImage(any(), any());
    }

    @Test
    void getUserBasic() {
        UserBasicDTO userBasicDTO = getBasicDto();
        when(userFollowRepository.getFollowingAndFollowersCount(any())).thenReturn(mock(FFCountModel.class));
        when(userRepository.findByUsernameOrEmail(any(), any())).thenReturn(new User());
        when(userMapper.entityToBasic(any())).thenReturn(userBasicDTO);
        UserBasicDTO response = userService.getUserBasic("example");
        assertEquals(userBasicDTO.getImage(), response.getImage());
        assertEquals(userBasicDTO.getName(), response.getName());
        assertEquals(userBasicDTO.getUsername(), response.getUsername());
        verify(userRepository, times(1)).findByUsernameOrEmail(any(), any());
    }

    @Test
    void getUserBasicError() {
        SoSuException exception = assertThrows(SoSuException.class, () -> userService.getUserBasic("ASD"));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("User name can not find", exception.getReason());
        assertEquals("USR_NOT_FOUND", exception.getCause().getMessage());
    }

    @Test
    void getFollowedUsers() {
        UserBasicDTO userBasicDTO = getBasicDto();
        when(userRepository.findByUsernameOrEmail(any(), any())).thenReturn(new User());
        when(userFollowRepository.getFollowings(any())).thenReturn(new ArrayList<>());
        when(userRepository.findByUsernameIn(any())).thenReturn(new ArrayList<>());
        when(userMapper.entityListToBasicList(any())).thenReturn(Collections.singletonList(userBasicDTO));
        List<UserBasicDTO> userBasicDTOS = userService.getFollowedUsers("ASD");
        UserBasicDTO response = Objects.requireNonNull(userBasicDTOS).get(0);
        assertEquals(userBasicDTO.getImage(), response.getImage());
        assertEquals(userBasicDTO.getName(), response.getName());
        assertEquals(userBasicDTO.getUsername(), response.getUsername());
        verify(userRepository, times(1)).findByUsernameOrEmail(any(), any());
    }

    @Test
    void getFollowedUsersError() {
        SoSuException exception = assertThrows(SoSuException.class, () -> userService.getFollowedUsers("ASD"));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("User name can not find", exception.getReason());
        assertEquals("USR_NOT_FOUND", exception.getCause().getMessage());
    }

    @Test
    void getFollowerUsers() {
        UserBasicDTO userBasicDTO = getBasicDto();
        when(userRepository.findByUsernameOrEmail(any(), any())).thenReturn(new User());
        when(userFollowRepository.getFollowers(any())).thenReturn(new ArrayList<>());
        when(userRepository.findByUsernameIn(any())).thenReturn(new ArrayList<>());
        when(userMapper.entityListToBasicList(any())).thenReturn(Collections.singletonList(userBasicDTO));
        List<UserBasicDTO> userBasicDTOS = userService.getFollowerUsers("ASD");
        UserBasicDTO response = Objects.requireNonNull(userBasicDTOS).get(0);
        assertEquals(userBasicDTO.getImage(), response.getImage());
        assertEquals(userBasicDTO.getName(), response.getName());
        assertEquals(userBasicDTO.getUsername(), response.getUsername());
        verify(userRepository, times(1)).findByUsernameOrEmail(any(), any());
    }

    @Test
    void getFollowerUsersError() {
        SoSuException exception = assertThrows(SoSuException.class, () -> userService.getFollowerUsers("ASD"));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("User name can not find", exception.getReason());
        assertEquals("USR_NOT_FOUND", exception.getCause().getMessage());
    }

    @Test
    void setFollowUser() {
        UserFollowRequest userFollowRequest = new UserFollowRequest();
        userFollowRequest.setFollower("");
        userFollowRequest.setFollowed("");
        when(userRepository.findByUsernameOrEmail(any(), any())).thenReturn(new User());
        userService.setFollowUser(userFollowRequest);
        verify(userFollowRepository, times(1)).save(any());
    }

    @Test
    void setFollowUserDelete() {
        UserFollowRequest userFollowRequest = new UserFollowRequest();
        userFollowRequest.setFollower("");
        userFollowRequest.setFollowed("");
        when(userRepository.findByUsernameOrEmail(any(), any())).thenReturn(new User());
        when(userFollowRepository.findById(any())).thenReturn(Optional.of(new UserFollow()));
        userService.setFollowUser(userFollowRequest);
        verify(userFollowRepository, times(1)).delete(any());
    }

    private UserBasicDTO getBasicDto() {
        UserBasicDTO userBasicDTO = new UserBasicDTO();
        userBasicDTO.setName("example");
        userBasicDTO.setImage("exampleImage");
        userBasicDTO.setUsername("exampleUserName");
        return userBasicDTO;
    }
}