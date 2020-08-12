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

    @Override
    public ResponseEntity<UserModel> login(AuthRequest authRequest) {
        authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        UserModel userDetails = userService.getUserDetails(authRequest.getUsername());
        jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(userDetails);
    }

    @Override
    public ResponseEntity<Void> register(UserRegisterRequest registerRequest) {
        userService.signUpUser(registerRequest);
        return ResponseEntity.noContent().build();
    }

    @Override
    @SoSuValidated
    public ResponseEntity<Void> validate(String username, String token) {
        userService.validate(username, token);
        return ResponseEntity.noContent().build();
    }
}
