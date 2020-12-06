/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.controller;

import com.sosu.rest.backend.controller.impl.ProductControllerImpl;
import com.sosu.rest.backend.enums.ProductField;
import com.sosu.rest.backend.enums.ProductType;
import com.sosu.rest.backend.model.CommonProductDetailDTO;
import com.sosu.rest.backend.model.CommonProductDTO;
import com.sosu.rest.backend.model.request.ProductByCategorySearchRequest;
import com.sosu.rest.backend.service.product.GamesService;
import com.sosu.rest.backend.service.product.ProductService;
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
        when(gamesService.getProductByCategory(any(ProductByCategorySearchRequest.class))).thenReturn(Collections.singletonList(new CommonProductDTO()));
        ResponseEntity<List<CommonProductDTO>> responseEntity = productController.getProductByCategory(createMockRequestObject());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, Objects.requireNonNull(responseEntity.getBody()).size());
    }

    @Test
    void getProductByCategoryGame() {
        when(productService.getProductByCategory(any(ProductByCategorySearchRequest.class))).thenReturn(Collections.singletonList(new CommonProductDTO()));
        ProductByCategorySearchRequest request = createMockRequestObject();
        request.setProductType(ProductType.PRODUCT);
        ResponseEntity<List<CommonProductDTO>> responseEntity = productController.getProductByCategory(request);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, Objects.requireNonNull(responseEntity.getBody()).size());
    }

    @Test
    void getProductByCategoryEmptyList() {
        ResponseEntity<List<CommonProductDTO>> responseEntity = productController.getProductByCategory(createMockRequestObject());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(0, Objects.requireNonNull(responseEntity.getBody()).size());
    }

    @Test
    void getRandomProducts() {
        when(productService.findRandomProduct(any())).thenReturn(Collections.singletonList(new CommonProductDTO("")));
        when(gamesService.findRandomGame(any())).thenReturn(Collections.singletonList(new CommonProductDTO("")));
        ResponseEntity<List<CommonProductDTO>> responseEntity = productController.getRandomProducts(1);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, Objects.requireNonNull(responseEntity.getBody()).size());
    }

    @Test
    void getRandomProducts_many() {
        when(productService.findRandomProduct(any())).thenReturn(Collections.nCopies(10, new CommonProductDTO("")));
        when(gamesService.findRandomGame(any())).thenReturn(Collections.nCopies(10, new CommonProductDTO("")));
        ResponseEntity<List<CommonProductDTO>> responseEntity = productController.getRandomProducts(1);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(10, Objects.requireNonNull(responseEntity.getBody()).size());
    }

    @Test
    void getProductDetail() {
        CommonProductDetailDTO commonProductModel = new CommonProductDetailDTO();
        commonProductModel.setName("exampleproduct");
        when(productService.findProduct(1L)).thenReturn(commonProductModel);
        ResponseEntity<CommonProductDetailDTO> responseEntity = productController.getProductDetail(1L, ProductType.PRODUCT);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("exampleproduct", Objects.requireNonNull(responseEntity.getBody()).getName());

    }

    @Test
    void getProductDetailGame() {
        CommonProductDetailDTO commonProductModel = new CommonProductDetailDTO();
        commonProductModel.setName("examplegame");
        when(gamesService.findGame(1L)).thenReturn(commonProductModel);
        ResponseEntity<CommonProductDetailDTO> responseEntity = productController.getProductDetail(1L, ProductType.GAME);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("examplegame", Objects.requireNonNull(responseEntity.getBody()).getName());

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