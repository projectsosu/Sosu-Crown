/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.controller.impl;

import com.sosu.rest.crown.controller.UserController;
import com.sosu.rest.crown.core.annotations.Security;
import com.sosu.rest.crown.core.annotations.SoSuValidated;
import com.sosu.rest.crown.core.exception.SoSuException;
import com.sosu.rest.crown.core.exception.SoSuSecurityException;
import com.sosu.rest.crown.core.util.JWTUtil;
import com.sosu.rest.crown.model.user.AuthRequest;
import com.sosu.rest.crown.model.user.UserBasicDTO;
import com.sosu.rest.crown.model.user.UserFollowRequest;
import com.sosu.rest.crown.model.user.UserModel;
import com.sosu.rest.crown.model.user.UserRegisterRequest;
import com.sosu.rest.crown.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserControllerImpl implements UserController {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private UserService userService;

    @Value("${sosu.supported.types}")
    private String supportedTypes;

    /**
     * Login request
     *
     * @param authRequest user auth information
     * @return user model if success
     */
    @Override
    public ResponseEntity<UserModel> login(AuthRequest authRequest) {
        authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        UserModel userDetails = userService.getUserDetails(authRequest.getUsername());
        jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(userDetails);
    }

    /**
     * Register new user
     *
     * @param registerRequest user registration request
     * @return void
     */
    @Override
    public ResponseEntity<Void> register(UserRegisterRequest registerRequest, Locale locale) {
        userService.signUpUser(registerRequest, locale);
        return ResponseEntity.noContent().build();
    }

    /**
     * Validate user email
     *
     * @param username of user
     * @param token    of mail
     * @return void
     */
    @Override
    @Security
    public ResponseEntity<Void> validate(String username, String token) {
        userService.validate(username, token);
        return ResponseEntity.noContent().build();
    }

    /**
     * Upload user profile image
     *
     * @param username of user
     * @param file     of image
     * @return void
     */
    @Override
    @Security
    public ResponseEntity<Void> uploadFile(MultipartFile file, String username) {
        if (!supportedTypes.contains(Objects.requireNonNull(file.getContentType()))) {
            throw new SoSuException(HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                    "Unsupported media type.You can upload JPG, JPEG or PNG images", "UNSUPPORTED_FILE");
        }
        try {
            userService.uploadImage(file.getBytes(), username);
        } catch (IOException e) {
            throw new SoSuException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage(), "UPLOAD_ERROR");
        }
        return ResponseEntity.noContent().build();
    }

    /**
     * @param username of user
     * @return user basic information
     */
    @Override
    @Security
    public ResponseEntity<UserBasicDTO> getUserBasic(String username) {
        return ResponseEntity.ok(userService.getUserBasic(username));
    }

    /**
     * @param username of user
     * @return following user basic information
     */
    @Override
    @Security
    public ResponseEntity<List<UserBasicDTO>> getFollowedUsers(String username) {
        return ResponseEntity.ok(userService.getFollowedUsers(username));
    }

    /**
     * @param username of user
     * @return follower user basic information
     */
    @Override
    @Security
    public ResponseEntity<List<UserBasicDTO>> getFollowerUsers(String username) {
        return ResponseEntity.ok(userService.getFollowerUsers(username));
    }

    @Override
    @SoSuValidated
    public ResponseEntity<Void> followUser(UserFollowRequest request, String jwtToken) {
        String username = jwtUtil.extractUsername(jwtToken);
        if (!request.getFollower().equals(username)) {
            throw new SoSuSecurityException(HttpStatus.UNAUTHORIZED, "User can not have required permission for this process", "UNAUTHORIZED_PROCESS");
        }
        userService.setFollowUser(request);
        return ResponseEntity.noContent().build();
    }

}
