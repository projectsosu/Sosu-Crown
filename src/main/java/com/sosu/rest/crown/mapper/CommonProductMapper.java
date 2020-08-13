/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.mapper;

import com.sosu.rest.crown.entity.postgres.Game;
import com.sosu.rest.crown.entity.postgres.Product;
import com.sosu.rest.crown.enums.ProductType;
import com.sosu.rest.crown.model.CommonProductModel;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Arrays;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CommonProductMapper {

    List<CommonProductModel> productsToCommon(List<Product> products);

    List<CommonProductModel> gamesToCommon(List<Game> games);

    @AfterMapping
    default void afterMap(@MappingTarget CommonProductModel commonProductModel, Product product) {
        if (product != null && StringUtils.isNotEmpty(product.getCategoryId())) {
            commonProductModel.setCategories(Arrays.asList(product.getCategoryId().split(";")));
        }
        commonProductModel.setProductType(ProductType.PRODUCT);
    }

    @AfterMapping
    default void afterMap(@MappingTarget CommonProductModel commonProductModel, Game game) {
        if (game != null && StringUtils.isNotEmpty(game.getCategoryId())) {
            commonProductModel.setCategories(Arrays.asList(game.getCategoryId().split(";")));
        }
        commonProductModel.setProductType(ProductType.GAME);
    }

}
