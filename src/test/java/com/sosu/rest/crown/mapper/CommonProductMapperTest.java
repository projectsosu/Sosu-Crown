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
import com.sosu.rest.crown.model.CommonProductDTO;
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
        CommonProductDTO commonProductDTO = new CommonProductDTO();
        commonProductMapper.afterMap(commonProductDTO, new Product(), categoryService);
        assertEquals(ProductType.PRODUCT, commonProductDTO.getProductType());
    }

    @Test
    void afterMap_game() {
        CommonProductDTO commonProductDTO = new CommonProductDTO();
        Product product = new Product();
        product.setCategoryId("asdasd");
        commonProductMapper.afterMap(commonProductDTO, product, categoryService);
        assertEquals(ProductType.PRODUCT, commonProductDTO.getProductType());
    }

    @Test
    void testAfterMap() {
        CommonProductDTO commonProductDTO = new CommonProductDTO();
        commonProductMapper.afterMap(commonProductDTO, new Game(), categoryService);
        assertEquals(ProductType.GAME, commonProductDTO.getProductType());
    }

    @Test
    void testAfterMap_game() {
        CommonProductDTO commonProductDTO = new CommonProductDTO();
        Game game = new Game();
        game.setCategoryId("asdasd");
        commonProductMapper.afterMap(commonProductDTO, game, categoryService);
        assertEquals(ProductType.GAME, commonProductDTO.getProductType());
    }

    @Test
    void productsToCommon() {
        List<CommonProductDTO> commonProductDTOS = commonProductMapper.productsToCommon(Collections.nCopies(5, new Product()), categoryService);
        assertEquals(5, commonProductDTOS.size());
    }

    @Test
    void gamesToCommon() {
        List<CommonProductDTO> commonProductDTOS = commonProductMapper.gamesToCommon(Collections.nCopies(5, new Game()), categoryService);
        assertEquals(5, commonProductDTOS.size());
    }
}