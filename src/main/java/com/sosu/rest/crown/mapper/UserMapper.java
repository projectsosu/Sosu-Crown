package com.sosu.rest.crown.mapper;

import com.sosu.rest.crown.core.service.SoSuPasswordEncoder;
import com.sosu.rest.crown.entity.mongo.User;
import com.sosu.rest.crown.model.user.UserModel;
import com.sosu.rest.crown.model.user.UserRegisterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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

}
