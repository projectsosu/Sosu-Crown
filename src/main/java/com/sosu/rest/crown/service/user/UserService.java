package com.sosu.rest.crown.service.user;

import com.sosu.rest.crown.model.user.UserModel;
import com.sosu.rest.crown.model.user.UserRegisterRequest;

public interface UserService {

    UserModel getUserDetails(String username);

    void signUpUser(UserRegisterRequest userRegisterRequest);

    void validate(String username, String token);
}
