package com.sosu.rest.crown.controller.impl;

import com.sosu.rest.crown.entity.postgres.Product;
import com.sosu.rest.crown.enums.ProductField;
import com.sosu.rest.crown.model.ProductByCategorySearchRequest;
import com.sosu.rest.crown.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerImplTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductControllerImpl productController;

    @Test
    void testGetProducts() {
        Product product = new Product();
        product.setName("Example");
        when(productService.getProductByNameAndYear(anyString(), anyInt())).thenReturn(product);
        assertEquals(productController.getProducts(anyString(), anyInt()).getName(), "Example");
    }

    @Test
    void testGetProductByCategory() {
        ProductByCategorySearchRequest productByCategorySearchRequest = new ProductByCategorySearchRequest();
        productByCategorySearchRequest.setCategory_id("");
        productByCategorySearchRequest.setPageSize(0);
        productByCategorySearchRequest.setSortBy(ProductField.ID);
        productByCategorySearchRequest.setDesc(false);

        when(productService.getProductByCategory(anyString(), anyInt(), anyString(), anyBoolean())).thenReturn(new ArrayList<>());
        assertNotNull(productController.getProductByCategory(productByCategorySearchRequest));
    }

}