/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.service.user;

import com.sosu.rest.crown.model.user.UserModel;
import com.sosu.rest.crown.model.user.UserRegisterRequest;

public interface UserService {

    UserModel getUserDetails(String username);

    void signUpUser(UserRegisterRequest userRegisterRequest);

    void validate(String username, String token);

    void uploadImage(byte[] image, String username);
}
