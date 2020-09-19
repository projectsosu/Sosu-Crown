/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.service.user;

import com.sosu.rest.crown.entity.mongo.User;
import com.sosu.rest.crown.model.user.UserBasicDTO;
import com.sosu.rest.crown.model.user.UserFollowRequest;
import com.sosu.rest.crown.model.user.UserModel;
import com.sosu.rest.crown.model.user.UserRegisterRequest;

import java.util.List;
import java.util.Locale;

public interface UserService {

    UserModel getUserDetails(String username);

    void signUpUser(UserRegisterRequest userRegisterRequest, Locale locale);

    void validate(String username, String token);

    void uploadImage(byte[] image, String username);

    UserBasicDTO getUserBasic(String username);

    List<UserBasicDTO> getFollowedUsers(String username);

    List<UserBasicDTO> getFollowerUsers(String username);

    void setFollowUser(UserFollowRequest userFollowRequest);

    User checkUserValidity(String username);
}
