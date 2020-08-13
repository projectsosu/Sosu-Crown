/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.service.user.impl;

import com.sosu.rest.crown.core.exception.SoSuException;
import com.sosu.rest.crown.core.service.ImageUploader;
import com.sosu.rest.crown.entity.mongo.Security;
import com.sosu.rest.crown.entity.mongo.User;
import com.sosu.rest.crown.mapper.UserMapper;
import com.sosu.rest.crown.model.user.UserModel;
import com.sosu.rest.crown.model.user.UserRegisterRequest;
import com.sosu.rest.crown.repo.mongo.SecurityRepository;
import com.sosu.rest.crown.repo.mongo.UserRepository;
import com.sosu.rest.crown.service.core.MailService;
import com.sosu.rest.crown.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * General user processes
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityRepository securityRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MailService mailService;

    @Autowired
    private ImageUploader imageUploader;

    /**
     * Gets user details
     *
     * @param username of searched user
     * @return user detail
     * @throws SoSuException if not find user
     */
    @Override
    public UserModel getUserDetails(String username) {
        User user = userRepository.findByUsernameOrEmail(username.toLowerCase(), username.toLowerCase());
        if (user == null) {
            throw new SoSuException(HttpStatus.BAD_REQUEST, "User name can not find", "USR_NOT_FOUND");
        }
        return userMapper.mapEntityToModel(user);
    }

    /**
     * Adds new user
     *
     * @param userRegisterRequest of new user
     * @throws SoSuException in case of already signed up
     */
    @Override
    public void signUpUser(UserRegisterRequest userRegisterRequest) {
        if (userRepository.findByUsername(userRegisterRequest.getUsername().toLowerCase()) != null) {
            throw new SoSuException(HttpStatus.BAD_REQUEST, "User already signed up", "USER_FOUND");
        }
        if (userRepository.findByEmail(userRegisterRequest.getEmail().toLowerCase()) != null) {
            throw new SoSuException(HttpStatus.BAD_REQUEST, "User already signed up", "EMAIL_FOUND");
        }

        User user = userMapper.registerRequestToModel(userRegisterRequest);
        userRepository.save(user);

        Security security = new Security();
        security.setUsername(userRegisterRequest.getUsername().toLowerCase());
        security.setTokenDate(LocalDateTime.now());
        security.setTtl(604800L);
        security.setToken(UUID.randomUUID().toString());
        securityRepository.save(security);

        mailService.sendRegisterMail(user.getEmail(), security.getToken());
    }

    /**
     * Validate user mail
     *
     * @param username of new user
     * @param token    of user
     * @throws SoSuException in any exception case
     */
    @Override
    public void validate(String username, String token) {
        User user = userRepository.findByUsernameOrEmail(username.toLowerCase(), username.toLowerCase());
        if (user == null) {
            throw new SoSuException(HttpStatus.BAD_REQUEST, "User not found", "USER_NOT_FOUND");
        }

        if (user.getValidated()) {
            throw new SoSuException(HttpStatus.BAD_REQUEST, "User already validated", "ALREADY_VALIDATED");
        }

        Security security = securityRepository.findByUsernameAndToken(username.toLowerCase(), token);
        if (security == null) {
            throw new SoSuException(HttpStatus.BAD_REQUEST, "Fields not valid", "USR_VALIDATION_ERROR");
        }
        if (LocalDateTime.now().minusDays(7).compareTo(security.getTokenDate()) > 0) {
            throw new SoSuException(HttpStatus.BAD_REQUEST, "Token out of date", "TOKEN_OUT_OF_DATE");
        }
        user.setValidated(Boolean.TRUE);
        userRepository.save(user);
        securityRepository.delete(security);
    }

    /**
     * Ths method upload profile image of user
     *
     * @param image    byte of user
     * @param username of user
     */
    @Override
    @Async
    public void uploadImage(byte[] image, String username) {
        User user = userRepository.findByUsernameOrEmail(username.toLowerCase(), username.toLowerCase());
        String url = imageUploader.uploadProfileImage(image, username);
        user.setImage(url);
        userRepository.save(user);
    }

}
