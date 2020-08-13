/**
 * @author : Oguz Kahraman
 * @since : 13.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.mapper;

import com.sosu.rest.crown.entity.postgres.Game;
import com.sosu.rest.crown.entity.postgres.Product;
import com.sosu.rest.crown.enums.ProductType;
import com.sosu.rest.crown.model.CommonProductModel;
import com.sosu.rest.crown.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CommonProductMapperTest {

    @Mock
    private CategoryService categoryService;

    private CommonProductMapper commonProductMapper;

    @BeforeEach
    void injectMap() {
        commonProductMapper = Mappers.getMapper(CommonProductMapper.class);
    }

    @Test
    void afterMap() {
        CommonProductModel commonProductModel = new CommonProductModel();
        commonProductMapper.afterMap(commonProductModel, new Product(), categoryService);
        assertEquals(ProductType.PRODUCT, commonProductModel.getProductType());
    }

    @Test
    void afterMap_game() {
        CommonProductModel commonProductModel = new CommonProductModel();
        Product product = new Product();
        product.setCategoryId("asdasd");
        commonProductMapper.afterMap(commonProductModel, product, categoryService);
        assertEquals(ProductType.PRODUCT, commonProductModel.getProductType());
    }

    @Test
    void testAfterMap() {
        CommonProductModel commonProductModel = new CommonProductModel();
        commonProductMapper.afterMap(commonProductModel, new Game(), categoryService);
        assertEquals(ProductType.GAME, commonProductModel.getProductType());
    }

    @Test
    void testAfterMap_game() {
        CommonProductModel commonProductModel = new CommonProductModel();
        Game game = new Game();
        game.setCategoryId("asdasd");
        commonProductMapper.afterMap(commonProductModel, game, categoryService);
        assertEquals(ProductType.GAME, commonProductModel.getProductType());
    }

    @Test
    void productsToCommon() {
        List<CommonProductModel> commonProductModels = commonProductMapper.productsToCommon(Collections.nCopies(5, new Product()), categoryService);
        assertEquals(5, commonProductModels.size());
    }

    @Test
    void gamesToCommon() {
        List<CommonProductModel> commonProductModels = commonProductMapper.gamesToCommon(Collections.nCopies(5, new Game()), categoryService);
        assertEquals(5, commonProductModels.size());
    }
}