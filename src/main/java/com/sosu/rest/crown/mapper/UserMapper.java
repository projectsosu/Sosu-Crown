package com.sosu.rest.crown.mapper;

import com.sosu.rest.crown.core.security.SoSuPasswordEncoder;
import com.sosu.rest.crown.entity.mongo.User;
import com.sosu.rest.crown.model.user.UserModel;
import com.sosu.rest.crown.model.user.UserRegisterRequest;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.time.LocalDate;

@Mapper(componentModel = "spring", uses = SoSuPasswordEncoder.class)
public interface UserMapper {

    UserModel mapEntityToModel(User user);

    @Mapping(target = "birthDate", source = "birthDate", qualifiedByName = "birthDateMapper")
    @Mapping(target = "password", source = "password", qualifiedByName = "passwordMapper")
    User registerRequestToModel(UserRegisterRequest registerRequest);

    @Named("birthDateMapper")
    default LocalDate birthDateMapper(String birthDate) {
        return LocalDate.parse(birthDate);
    }

    @AfterMapping
    default void afterMap(@MappingTarget User user, UserRegisterRequest registerRequest) {
        user.setUsername(registerRequest.getUsername().toLowerCase());
        user.setEmail(registerRequest.getEmail().toLowerCase());
        user.setName(StringUtils.capitalize(registerRequest.getName().toUpperCase()));
        user.setSurname(registerRequest.getName().toUpperCase());
    }
}
