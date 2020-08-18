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
import com.sosu.rest.crown.model.CommonProductDTO;
import com.sosu.rest.crown.model.CommonProductDetailDTO;
import com.sosu.rest.crown.service.CategoryService;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface CommonProductMapper {

    CommonProductDetailDTO productToCommon(Product product);

    CommonProductDetailDTO gameToCommon(Game product);

    List<CommonProductDTO> productsToCommon(List<Product> products, @Context CategoryService categoryService);

    List<CommonProductDTO> gamesToCommon(List<Game> games, @Context CategoryService categoryService);

    @AfterMapping
    default void afterMap(@MappingTarget CommonProductDTO commonProductDTO, Product product, @Context CategoryService categoryService) {
        if (product != null && StringUtils.isNotEmpty(product.getCategoryId())) {
            commonProductDTO.setCategories(new HashSet<>(Arrays.asList(product.getCategoryId().split(";"))));
            createCategoryNameList(commonProductDTO, categoryService);
        }
        commonProductDTO.setProductType(ProductType.PRODUCT);
    }

    @AfterMapping
    default void afterMap(@MappingTarget CommonProductDTO commonProductDTO, Game game, @Context CategoryService categoryService) {
        if (game != null && StringUtils.isNotEmpty(game.getCategoryId())) {
            commonProductDTO.setCategories(new HashSet<>(Arrays.asList(game.getCategoryId().split(";"))));
            createCategoryNameList(commonProductDTO, categoryService);
        }
        commonProductDTO.setProductType(ProductType.GAME);
    }

    @AfterMapping
    default void afterMap(@MappingTarget CommonProductDTO commonProductDTO, Product product) {
        commonProductDTO.setProductType(ProductType.PRODUCT);
    }

    @AfterMapping
    default void afterMap(@MappingTarget CommonProductDTO commonProductDTO, Game game) {
        commonProductDTO.setProductType(ProductType.GAME);
    }

    private void createCategoryNameList(@MappingTarget CommonProductDTO commonProductDTO, @Context CategoryService categoryService) {
        Set<String> categoryNames = new HashSet<>();
        commonProductDTO.getCategories().stream().parallel().forEach(item -> {
            if (categoryService.getCategoryName(item) != null) {
                categoryNames.add(categoryService.getCategoryName(item));
            }
        });
        commonProductDTO.setCategoryNames(categoryNames);
    }

}
