package com.sosu.rest.crown.mapper;

import com.sosu.rest.crown.entity.postgres.Game;
import com.sosu.rest.crown.enums.ProductTypeEnum;
import com.sosu.rest.crown.model.SearchResponseModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GamesMapper {

    List<SearchResponseModel> entityToModel(List<Game> games);

    @AfterMapping
    default void afterMap(@MappingTarget SearchResponseModel searchResponseModel) {
        searchResponseModel.setProductTypeEnum(ProductTypeEnum.GAME);
    }

}
