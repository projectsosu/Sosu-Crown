/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.core.security;

import com.sosu.rest.crown.core.annotations.Security;
import com.sosu.rest.crown.core.annotations.SoSuValidated;
import com.sosu.rest.crown.core.exception.SoSuException;
import com.sosu.rest.crown.core.exception.SoSuSecurityException;
import com.sosu.rest.crown.core.util.JWTUtil;
import com.sosu.rest.crown.entity.mongo.User;
import com.sosu.rest.crown.repo.mongo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SecurityCheckerInterceptor implements HandlerInterceptor {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    /**
     * This method checks if request is secure or not
     *
     * @param request  request object
     * @param response response object
     * @param handler  handler of method
     * @return is filter success or not
     */
    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler) {
        HandlerMethod hm = (HandlerMethod) handler;
        if (hm.getMethod().getDeclaredAnnotation(SoSuValidated.class) != null || hm.getMethod().getDeclaredAnnotation(Security.class) != null) {
            String token = request.getHeader("Bearer-Token");
            if (token == null) {
                throw new SoSuSecurityException(HttpStatus.UNAUTHORIZED, "Token can not be null", "TOKEN_NULL");
            }
            if (!jwtUtil.validateToken(token)) {
                throw new SoSuSecurityException(HttpStatus.UNAUTHORIZED, "User token invalid", "TOKEN_INVALID");
            }
            if (hm.getMethod().getDeclaredAnnotation(SoSuValidated.class) != null) {
                String username = jwtUtil.extractUsername(token);
                User user = userRepository.findByUsername(username.toLowerCase());
                if (user == null) {
                    throw new SoSuException(HttpStatus.BAD_REQUEST, "User not found", "USER_NOT_FOUND");
                }
                if (!user.getValidated()) {
                    throw new SoSuException(HttpStatus.UNAUTHORIZED, "User not validated", "NOT_VALIDATED");
                }
            }
        }
        return true;
    }

}
