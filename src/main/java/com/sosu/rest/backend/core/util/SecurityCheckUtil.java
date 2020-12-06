/**
 * @author : Oguz Kahraman
 * @since : 19.09.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.core.util;

import com.sosu.rest.backend.core.exception.SoSuSecurityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SecurityCheckUtil {

    @Autowired
    private JWTUtil jwtUtil;

    public void checkUserValidity(String userName, String jwtToken) {
        String username = jwtUtil.extractUsername(jwtToken);
        if (!userName.equals(username)) {
            throw new SoSuSecurityException(HttpStatus.UNAUTHORIZED, "User can not have required permission for this process", "UNAUTHORIZED_PROCESS");
        }
    }

}
