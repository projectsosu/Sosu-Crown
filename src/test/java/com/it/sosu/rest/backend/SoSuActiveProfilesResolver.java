/**
 * @author : Oguz Kahraman
 * @since : 6.12.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.it.sosu.rest.backend;
import org.springframework.test.context.ActiveProfilesResolver;

public class SoSuActiveProfilesResolver implements ActiveProfilesResolver {

    @Override
    public String[] resolve(Class<?> aClass) {
        String activeProfiles = System.getProperty("spring.profiles.active");
        return new String[]{activeProfiles != null ? activeProfiles : "dev"};
    }
}

