/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.controller.impl;

import com.sosu.rest.crown.controller.UserController;
import com.sosu.rest.crown.core.annotations.Security;
import com.sosu.rest.crown.core.util.JWTUtil;
import com.sosu.rest.crown.model.user.AuthRequest;
import com.sosu.rest.crown.model.user.UserModel;
import com.sosu.rest.crown.model.user.UserRegisterRequest;
import com.sosu.rest.crown.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<Void> register(UserRegisterRequest registerRequest) {
        userService.signUpUser(registerRequest);
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
        userService.uploadImage(file, username);
        return ResponseEntity.noContent().build();
    }

}
