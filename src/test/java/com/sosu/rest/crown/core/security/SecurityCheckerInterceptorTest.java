/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.core.security;

import com.sosu.rest.crown.core.annotations.SoSuValidated;
import com.sosu.rest.crown.core.exception.SoSuException;
import com.sosu.rest.crown.core.exception.SoSuSecurityException;
import com.sosu.rest.crown.core.util.JWTUtil;
import com.sosu.rest.crown.entity.mongo.User;
import com.sosu.rest.crown.repo.mongo.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SecurityCheckerInterceptorTest {

    @Mock
    private JWTUtil jwtUtil;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SecurityCheckerInterceptor securityCheckerInterceptor;

    @Test
    void preHandle() {
        HandlerMethod method = mock(HandlerMethod.class);
        when(method.getMethod()).thenReturn(mock(Method.class));
        Boolean bol = securityCheckerInterceptor.preHandle(mock(HttpServletRequest.class), mock(HttpServletResponse.class), method);
        assertTrue(bol);
    }

    @Test
    void preHandle_secureAnnotationException() {
        HandlerMethod method = mock(HandlerMethod.class);
        Method method1 = mock(Method.class);
        when(method.getMethod()).thenReturn(method1);
        when(method1.getDeclaredAnnotation(SoSuValidated.class)).thenReturn(mock(SoSuValidated.class));
        SoSuSecurityException exception = assertThrows(SoSuSecurityException.class,
                () -> securityCheckerInterceptor.preHandle(mock(HttpServletRequest.class), mock(HttpServletResponse.class), method));
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());
        assertEquals("TOKEN_NULL", exception.getCause().getMessage());
    }

    @Test
    void preHandle_secureAnnotationWithoutToken() {
        when(jwtUtil.validateToken(any())).thenReturn(false);
        HandlerMethod method = mock(HandlerMethod.class);
        Method method1 = mock(Method.class);
        when(method.getMethod()).thenReturn(method1);
        when(method1.getDeclaredAnnotation(SoSuValidated.class)).thenReturn(mock(SoSuValidated.class));
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Bearer-Token")).thenReturn("123123122312331");
        SoSuSecurityException exception = assertThrows(SoSuSecurityException.class,
                () -> securityCheckerInterceptor.preHandle(request, mock(HttpServletResponse.class), method));
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());
        assertEquals("TOKEN_INVALID", exception.getCause().getMessage());
    }

    @Test
    void preHandle_secureAnnotationWithToken() {
        when(jwtUtil.validateToken(anyString())).thenReturn(true);
        when(jwtUtil.extractUsername(any())).thenReturn("123");
        when(userRepository.findByUsername("123")).thenReturn(null);
        HandlerMethod method = mock(HandlerMethod.class);
        Method method1 = mock(Method.class);
        when(method.getMethod()).thenReturn(method1);
        when(method1.getDeclaredAnnotation(SoSuValidated.class)).thenReturn(mock(SoSuValidated.class));
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Bearer-Token")).thenReturn("123123122312331");
        SoSuException exception = assertThrows(SoSuException.class,
                () -> securityCheckerInterceptor.preHandle(request, mock(HttpServletResponse.class), method));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("USER_NOT_FOUND", exception.getCause().getMessage());
    }

    @Test
    void preHandle_secureAnnotationWithTokenUser() {
        when(jwtUtil.validateToken(anyString())).thenReturn(true);
        when(jwtUtil.extractUsername(any())).thenReturn("123");
        when(userRepository.findByUsername("123")).thenReturn(mock(User.class));
        HandlerMethod method = mock(HandlerMethod.class);
        Method method1 = mock(Method.class);
        when(method.getMethod()).thenReturn(method1);
        when(method1.getDeclaredAnnotation(SoSuValidated.class)).thenReturn(mock(SoSuValidated.class));
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Bearer-Token")).thenReturn("123123122312331");
        SoSuException exception = assertThrows(SoSuException.class,
                () -> securityCheckerInterceptor.preHandle(request, mock(HttpServletResponse.class), method));
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());
        assertEquals("NOT_VALIDATED", exception.getCause().getMessage());
    }
}