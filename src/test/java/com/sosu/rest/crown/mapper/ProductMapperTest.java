/**
 * @author : Oguz Kahraman
 * @since : 13.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.mapper;

import com.sosu.rest.crown.enums.ProductType;
import com.sosu.rest.crown.model.SearchResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ProductMapperTest {

    private ProductMapper productMapper;

    @BeforeEach
    void injectMap() {
        productMapper = Mappers.getMapper(ProductMapper.class);
    }

    @Test
    void afterMap() {
        SearchResponseModel searchResponseModel = new SearchResponseModel();
        productMapper.afterMap(searchResponseModel);
        assertEquals(ProductType.PRODUCT, searchResponseModel.getProductType());
    }
}