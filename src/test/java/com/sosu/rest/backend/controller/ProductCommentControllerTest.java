/**
 * @author : Oguz Kahraman
 * @since : 18.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.controller;

import com.sosu.rest.backend.controller.impl.ProductCommentControllerImpl;
import com.sosu.rest.backend.model.request.ProductCommentRequest;
import com.sosu.rest.backend.service.product.comment.ProductCommentService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

@ExtendWith(MockitoExtension.class)
class ProductCommentControllerTest {

    @Mock
    private ProductCommentService productCommentService;

    @InjectMocks
    private ProductCommentControllerImpl productController;

    @AfterEach
    void cleanTests() {
        reset(productCommentService);
    }

    @Test
    void getProductByCategory() {
        doNothing().when(productCommentService).addNewComment(any(), any(), any());
        ResponseEntity<Void> responseEntity = productController.getProductByCategory(mock(ProductCommentRequest.class), 123L, Locale.ENGLISH);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }
}