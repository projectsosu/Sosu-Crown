/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.service.user.impl;

import com.sosu.rest.backend.entity.postgres.UserFollow;
import com.sosu.rest.backend.entity.postgres.embeddedid.UserFollowId;
import com.sosu.rest.backend.model.service.FFCountModel;
import com.sosu.rest.backend.model.user.UserBasicDTO;
import com.sosu.rest.backend.model.user.UserFollowRequest;
import com.sosu.rest.backend.repo.postgres.UserFollowRepository;
import com.sosu.rest.backend.core.exception.SoSuException;
import com.sosu.rest.backend.core.service.ImageUploader;
import com.sosu.rest.backend.entity.mongo.Security;
import com.sosu.rest.backend.entity.mongo.User;
import com.sosu.rest.backend.mapper.UserMapper;
import com.sosu.rest.backend.model.user.UserModel;
import com.sosu.rest.backend.model.user.UserRegisterRequest;
import com.sosu.rest.backend.repo.mongo.SecurityRepository;
import com.sosu.rest.backend.repo.mongo.UserRepository;
import com.sosu.rest.backend.service.core.MailService;
import com.sosu.rest.backend.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * General user processes
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityRepository securityRepository;

    @Autowired
    private UserFollowRepository userFollowRepository;

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
        User user = checkUserValidity(username);
        return userMapper.mapEntityToModel(user);
    }

    /**
     * Adds new user
     *
     * @param userRegisterRequest of new user
     * @throws SoSuException in case of already signed up
     */
    @Override
    public void signUpUser(UserRegisterRequest userRegisterRequest, Locale locale) {
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

        mailService.sendRegisterMail(user, security.getToken(), locale);
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

    /**
     * @param username of user
     * @return user basic information
     */
    @Override
    public UserBasicDTO getUserBasic(String username) {
        User user = checkUserValidity(username);
        UserBasicDTO responseDto = userMapper.entityToBasic(user);
        FFCountModel counts = userFollowRepository.getFollowingAndFollowersCount(username);
        responseDto.setFollowedCount(counts.getFollowing());
        responseDto.setFollowerCount(counts.getFollower());
        return responseDto;
    }

    /**
     * Get user following users details
     *
     * @param username of user
     * @return following user basic information
     */
    @Override
    public List<UserBasicDTO> getFollowedUsers(String username) {
        checkUserValidity(username);
        List<String> strings = userFollowRepository.getFollowings(username);
        List<User> userList = userRepository.findByUsernameIn(strings);
        return userMapper.entityListToBasicList(userList);
    }

    /**
     * Get user follower users details
     *
     * @param username of user
     * @return follower user basic information
     */
    @Override
    public List<UserBasicDTO> getFollowerUsers(String username) {
        checkUserValidity(username);
        List<String> strings = userFollowRepository.getFollowers(username);
        List<User> userList = userRepository.findByUsernameIn(strings);
        return userMapper.entityListToBasicList(userList);
    }

    /**
     * Follow or unfollow user
     *
     * @param userFollowRequest for follow
     */
    @Override
    public void setFollowUser(UserFollowRequest userFollowRequest) {
        checkUserValidity(userFollowRequest.getFollowed());
        checkUserValidity(userFollowRequest.getFollower());
        UserFollowId userFollowId = new UserFollowId();
        userFollowId.setFollowedUserName(userFollowRequest.getFollowed());
        userFollowId.setUserName(userFollowRequest.getFollower());
        UserFollow userFollow = userFollowRepository.findById(userFollowId).orElse(null);
        if (userFollow != null) {
            userFollowRepository.delete(userFollow);
        } else {
            userFollow = new UserFollow();
            userFollow.setId(userFollowId);
            userFollowRepository.save(userFollow);
        }
    }

    /**
     * @param username of user
     * @return true if user exist otherwise returns false
     */
    @Override
    public User checkUserValidity(String username) {
        User user = userRepository.findByUsernameOrEmail(username.toLowerCase(), username.toLowerCase());
        if (user == null) {
            throw new SoSuException(HttpStatus.BAD_REQUEST, "User name can not find", "USR_NOT_FOUND");
        }
        return user;
    }

}