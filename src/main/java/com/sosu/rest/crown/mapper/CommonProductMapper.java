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
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface CommonProductMapper {

    @Mapping(source = "categoryIdList", target = "categories")
    @Mapping(source = "mainCategoryIdList", target = "mainCategories")
    CommonProductDetailDTO productToCommon(Product product, @Context CategoryService categoryService);

    @Mapping(source = "categoryIdList", target = "categories")
    @Mapping(source = "mainCategoryIdList", target = "mainCategories")
    @Mapping(source = "consoleCategoryIdList", target = "consoleCategories")
    CommonProductDetailDTO gameToCommon(Game product, @Context CategoryService categoryService);

    List<CommonProductDTO> productsToCommon(List<Product> products, @Context CategoryService categoryService);

    List<CommonProductDTO> gamesToCommon(List<Game> games, @Context CategoryService categoryService);

    @AfterMapping
    default void afterMap(@MappingTarget CommonProductDTO commonProductDTO, Product product, @Context CategoryService categoryService) {
        if (product != null) {
            createCategoryNameList(commonProductDTO, categoryService);
        }
        commonProductDTO.setProductType(ProductType.PRODUCT);
    }

    @AfterMapping
    default void afterMap(@MappingTarget CommonProductDTO commonProductDTO, Game game, @Context CategoryService categoryService) {
        if (game != null) {
            createCategoryNameList(commonProductDTO, categoryService);
        }
        commonProductDTO.setProductType(ProductType.GAME);
    }

    private void createCategoryNameList(@MappingTarget CommonProductDTO commonProductDTO, @Context CategoryService categoryService) {
        Set<String> categoryNames = new HashSet<>();
        Set<String> mainCategoryNames = new HashSet<>();
        Set<String> consoleCategoryNames = new HashSet<>();
        if (CollectionUtils.isNotEmpty(commonProductDTO.getCategories())) {
            commonProductDTO.getCategories().stream().parallel().forEach(item -> {
                if (categoryService.getCategoryName(item) != null) {
                    categoryNames.add(categoryService.getCategoryName(item));
                }
            });
        }
        if (CollectionUtils.isNotEmpty(commonProductDTO.getMainCategories())) {
            commonProductDTO.getMainCategories().stream().parallel().forEach(item -> {
                if (categoryService.getCategoryName(item) != null) {
                    mainCategoryNames.add(categoryService.getCategoryName(item));
                }
            });
        }
        if (CollectionUtils.isNotEmpty(commonProductDTO.getConsoleCategories())) {
            commonProductDTO.getConsoleCategories().stream().parallel().forEach(item -> {
                if (categoryService.getCategoryName(item) != null) {
                    consoleCategoryNames.add(categoryService.getCategoryName(item));
                }
            });
        }
        commonProductDTO.setCategoryNames(categoryNames);
        commonProductDTO.setMainCategories(mainCategoryNames);
        commonProductDTO.setConsoleCategoryNames(consoleCategoryNames);
    }

}
