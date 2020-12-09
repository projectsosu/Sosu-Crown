package com.sosu.rest.backend.core.security;

/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/

import com.sosu.rest.backend.core.exception.SoSuException;
import com.sosu.rest.backend.entity.mongo.User;
import com.sosu.rest.backend.repo.mongo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SoSuAuthManager implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) {
        User user = userRepository.findByUsernameOrEmail(authentication.getName().toLowerCase(), authentication.getName().toLowerCase());
        if (user == null) {
            throw new SoSuException(HttpStatus.BAD_REQUEST, "User name can not find", "USR_NOT_FOUND");
        }
        if (!passwordEncoder.matches(authentication.getCredentials().toString(), user.getPassword())) {
            throw new BadCredentialsException("User name or password didn't match.");
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}