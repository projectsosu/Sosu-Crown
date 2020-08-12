/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.controller.impl;

import com.sosu.rest.crown.controller.UserController;
import com.sosu.rest.crown.core.annotations.SoSuValidated;
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
     * @param registerRequest
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
     * @param username
     * @param token    of mail
     * @return void
     */
    @Override
    @SoSuValidated
    public ResponseEntity<Void> validate(String username, String token) {
        userService.validate(username, token);
        return ResponseEntity.noContent().build();
    }
}
