/**
 * @author : Oguz Kahraman
 * @since : 13.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.mapper;

import com.sosu.rest.backend.entity.mongo.User;
import com.sosu.rest.backend.model.user.UserRegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    void setUserMapper() {
        userMapper = Mappers.getMapper(UserMapper.class);
    }

    @Test
    void birthDateMapper() {
        LocalDate localDate = userMapper.birthDateMapper("1990-12-12");
        LocalDate expected = LocalDate.parse("1990-12-12");
        assertEquals(expected, localDate);
    }

    @Test
    void afterMap() {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("Email");
        userRegisterRequest.setName("name");
        userRegisterRequest.setUsername("username");
        User user = new User();
        userMapper.afterMap(user, userRegisterRequest);
        assertEquals("email", user.getEmail());
        assertEquals("Name", user.getName());
        assertEquals("username", user.getUsername());
    }

}