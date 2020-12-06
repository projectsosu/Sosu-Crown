/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.core.security;

import com.sosu.rest.backend.core.annotations.Security;
import com.sosu.rest.backend.core.annotations.SoSuValidated;
import com.sosu.rest.backend.core.exception.SoSuException;
import com.sosu.rest.backend.core.exception.SoSuSecurityException;
import com.sosu.rest.backend.entity.mongo.User;
import com.sosu.rest.backend.repo.mongo.UserRepository;
import com.sosu.rest.backend.core.util.JWTUtil;
import org.jetbrains.annotations.NotNull;
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
            @NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
        HandlerMethod hm = (HandlerMethod) handler;
        if (hm.getMethod().getDeclaredAnnotation(SoSuValidated.class) != null || hm.getMethod().getDeclaredAnnotation(Security.class) != null) {
            String token = request.getHeader("Authorization");
            if (token == null) {
                throw new SoSuSecurityException(HttpStatus.UNAUTHORIZED, "JWT token can not be null", "JWT_TOKEN_NULL");
            }
            if (!jwtUtil.validateToken(token)) {
                throw new SoSuSecurityException(HttpStatus.UNAUTHORIZED, "JWT token invalid", "JWT_TOKEN_INVALID");
            }
            String username = jwtUtil.extractUsername(token);
            User user = userRepository.findByUsername(username.toLowerCase());
            if (user == null) {
                throw new SoSuException(HttpStatus.BAD_REQUEST, "User not found", "USER_NOT_FOUND");
            }
            if (hm.getMethod().getDeclaredAnnotation(SoSuValidated.class) != null && !user.getValidated()) {
                throw new SoSuException(HttpStatus.UNAUTHORIZED, "User not validated", "NOT_VALIDATED");
            }
        }
        return true;
    }

}
