/**
 * @author : Oguz Kahraman
 * @since : 13.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.service;

import com.sosu.rest.crown.entity.postgres.Product;
import com.sosu.rest.crown.mapper.CommonProductMapper;
import com.sosu.rest.crown.model.CommonProductModel;
import com.sosu.rest.crown.model.ProductByCategorySearchRequest;
import com.sosu.rest.crown.repo.postgres.ProductRepository;
import com.sosu.rest.crown.service.product.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CommonProductMapper commonProductMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void saveOrUpdate() {
        productService.saveOrUpdate(new Product());
        verify(productRepository, times(1)).save(any());
    }

    @Test
    void getProductByCategory() {
        ProductByCategorySearchRequest productByCategorySearchRequest = new ProductByCategorySearchRequest();
        productByCategorySearchRequest.setDesc(true);
        when(commonProductMapper.productsToCommon(any(), any())).thenReturn(Collections.nCopies(5, new CommonProductModel()));
        List<CommonProductModel> commonProductModels = productService.getProductByCategory(productByCategorySearchRequest);
        assertEquals(5, Objects.requireNonNull(commonProductModels).size());
    }

    @Test
    void getProductByCategoryNonDesc() {
        ProductByCategorySearchRequest productByCategorySearchRequest = new ProductByCategorySearchRequest();
        productByCategorySearchRequest.setDesc(false);
        when(commonProductMapper.productsToCommon(any(), any())).thenReturn(Collections.nCopies(5, new CommonProductModel()));
        List<CommonProductModel> commonProductModels = productService.getProductByCategory(productByCategorySearchRequest);
        assertEquals(5, Objects.requireNonNull(commonProductModels).size());
    }

    @Test
    void findRandomProduct() {
        when(productRepository.findRandomProduct(any())).thenReturn(new ArrayList<>());
        when(commonProductMapper.productsToCommon(any(), any())).thenReturn(Collections.nCopies(5, new CommonProductModel()));
        List<CommonProductModel> commonProductModels = productService.findRandomProduct(1);
        assertEquals(5, Objects.requireNonNull(commonProductModels).size());
    }
}