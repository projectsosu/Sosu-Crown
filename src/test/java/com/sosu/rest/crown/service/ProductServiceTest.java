/**
 * @author : Oguz Kahraman
 * @since : 13.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.service;

import com.sosu.rest.crown.core.exception.SoSuException;
import com.sosu.rest.crown.entity.postgres.Product;
import com.sosu.rest.crown.mapper.CommonProductMapper;
import com.sosu.rest.crown.model.CommonProductDTO;
import com.sosu.rest.crown.model.CommonProductDetailDTO;
import com.sosu.rest.crown.model.ProductByCategorySearchRequest;
import com.sosu.rest.crown.repo.postgres.ProductRepository;
import com.sosu.rest.crown.service.product.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        when(commonProductMapper.productsToCommon(any(), any())).thenReturn(Collections.nCopies(5, new CommonProductDTO()));
        List<CommonProductDTO> commonProductDTOS = productService.getProductByCategory(productByCategorySearchRequest);
        assertEquals(5, Objects.requireNonNull(commonProductDTOS).size());
    }

    @Test
    void getProductByCategoryNonDesc() {
        ProductByCategorySearchRequest productByCategorySearchRequest = new ProductByCategorySearchRequest();
        productByCategorySearchRequest.setDesc(false);
        when(commonProductMapper.productsToCommon(any(), any())).thenReturn(Collections.nCopies(5, new CommonProductDTO()));
        List<CommonProductDTO> commonProductDTOS = productService.getProductByCategory(productByCategorySearchRequest);
        assertEquals(5, Objects.requireNonNull(commonProductDTOS).size());
    }

    @Test
    void findRandomProduct() {
        when(productRepository.findRandomProduct(any())).thenReturn(new ArrayList<>());
        when(commonProductMapper.productsToCommon(any(), any())).thenReturn(Collections.nCopies(5, new CommonProductDTO()));
        List<CommonProductDTO> commonProductDTOS = productService.findRandomProduct(1);
        assertEquals(5, Objects.requireNonNull(commonProductDTOS).size());
    }

    @Test
    void findProduct() {
        CommonProductDetailDTO commonProductDTO = new CommonProductDetailDTO();
        commonProductDTO.setName("example");
        when(productRepository.findById(any())).thenReturn(Optional.of(new Product()));
        when(commonProductMapper.productToCommon(any(), any())).thenReturn(commonProductDTO);
        CommonProductDetailDTO commonProductModels = productService.findProduct(1L);
        assertEquals("example", commonProductModels.getName());
    }

    @Test
    void findProductError() {
        when(productRepository.findById(any())).thenReturn(Optional.empty());
        SoSuException exception = assertThrows(SoSuException.class, () -> productService.findProduct(1L));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Product can not find", exception.getReason());
        assertEquals("PRODUCT_NOT_FOUND", exception.getCause().getMessage());
    }

    @Test
    void save() {
        productService.saveOrUpdate(new Product());
        verify(productRepository, times(1)).save(any());
    }

}