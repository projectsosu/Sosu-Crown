/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.core.security;

import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SoSuPasswordEncoder {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Named("passwordMapper")
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
