package com.sosu.rest.crown.core.service;

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
