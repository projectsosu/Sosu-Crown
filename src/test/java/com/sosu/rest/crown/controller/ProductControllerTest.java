package com.sosu.rest.crown.controller;

import com.sosu.rest.crown.controller.impl.ProductControllerImpl;
import com.sosu.rest.crown.enums.ProductField;
import com.sosu.rest.crown.enums.ProductType;
import com.sosu.rest.crown.model.CommonProductModel;
import com.sosu.rest.crown.model.ProductByCategorySearchRequest;
import com.sosu.rest.crown.service.GamesService;
import com.sosu.rest.crown.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private GamesService gamesService;

    @InjectMocks
    private ProductControllerImpl productController;

    @Test
    void getProductByCategory() {
        when(gamesService.getProductByCategory(any(ProductByCategorySearchRequest.class))).thenReturn(Collections.singletonList(new CommonProductModel()));
        ResponseEntity<List<CommonProductModel>> responseEntity = productController.getProductByCategory(createMockRequestObject());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, Objects.requireNonNull(responseEntity.getBody()).size());
    }

    @Test
    void getProductByCategoryGame() {
        when(productService.getProductByCategory(any(ProductByCategorySearchRequest.class))).thenReturn(Collections.singletonList(new CommonProductModel()));
        ProductByCategorySearchRequest request = createMockRequestObject();
        request.setProductType(ProductType.PRODUCT);
        ResponseEntity<List<CommonProductModel>> responseEntity = productController.getProductByCategory(request);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, Objects.requireNonNull(responseEntity.getBody()).size());
    }

    @Test
    void getProductByCategoryEmptyList() {
        ResponseEntity<List<CommonProductModel>> responseEntity = productController.getProductByCategory(createMockRequestObject());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(0, Objects.requireNonNull(responseEntity.getBody()).size());
    }

    ProductByCategorySearchRequest createMockRequestObject() {
        ProductByCategorySearchRequest productByCategorySearchRequest = new ProductByCategorySearchRequest();
        productByCategorySearchRequest.setCategoryId("");
        productByCategorySearchRequest.setProductType(ProductType.GAME);
        productByCategorySearchRequest.setPage(0);
        productByCategorySearchRequest.setPageSize(0);
        productByCategorySearchRequest.setSortBy(ProductField.DESCRIPTION);
        productByCategorySearchRequest.setDesc(false);
        return productByCategorySearchRequest;
    }

}