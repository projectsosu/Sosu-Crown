package com.sosu.rest.crown.core.security;

import com.sosu.rest.crown.core.annotations.Security;
import com.sosu.rest.crown.repo.mongo.SecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SecurityCheckerInterceptor implements HandlerInterceptor {

    @Autowired
    private SecurityRepository securityRepository;

    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod hm = (HandlerMethod) handler;
        if (hm.getBeanType().getDeclaredAnnotation(Security.class) != null) {
            String token = request.getHeader("Bearer-Token");
            String username = request.getHeader("Username");
            if (token == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Send Bearer and Username on header");
                return false;
            }
            if (securityRepository.findByUsernameAndToken(username, token) == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("User token invalid");
                return false;
            }
        }
        return true;
    }

}
