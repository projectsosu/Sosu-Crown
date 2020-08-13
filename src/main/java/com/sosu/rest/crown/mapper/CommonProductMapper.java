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
import com.sosu.rest.crown.service.CategoryService;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CommonProductMapper {

    List<CommonProductModel> productsToCommon(List<Product> products, @Context CategoryService categoryService);

    List<CommonProductModel> gamesToCommon(List<Game> games, @Context CategoryService categoryService);

    @AfterMapping
    default void afterMap(@MappingTarget CommonProductModel commonProductModel, Product product, @Context CategoryService categoryService) {
        if (product != null && StringUtils.isNotEmpty(product.getCategoryId())) {
            commonProductModel.setCategories(Arrays.asList(product.getCategoryId().split(";")));
            createCategoryNameList(commonProductModel, categoryService);
        }
        commonProductModel.setProductType(ProductType.PRODUCT);
    }

    @AfterMapping
    default void afterMap(@MappingTarget CommonProductModel commonProductModel, Game game, @Context CategoryService categoryService) {
        if (game != null && StringUtils.isNotEmpty(game.getCategoryId())) {
            commonProductModel.setCategories(Arrays.asList(game.getCategoryId().split(";")));
            createCategoryNameList(commonProductModel, categoryService);
        }
        commonProductModel.setProductType(ProductType.GAME);
    }

    private void createCategoryNameList(@MappingTarget CommonProductModel commonProductModel, @Context CategoryService categoryService) {
        ArrayList<String> categoryNames = new ArrayList<>();
        commonProductModel.getCategories().stream().parallel().forEach(item -> {
            if (categoryService.getCategoryName(item) != null) {
                categoryNames.add(categoryService.getCategoryName(item));
            }
        });
        commonProductModel.setCategoryNames(categoryNames);
    }

}
