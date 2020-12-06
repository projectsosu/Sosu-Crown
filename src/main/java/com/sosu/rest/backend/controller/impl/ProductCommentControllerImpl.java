/**
 * @author : Oguz Kahraman
 * @since : 18.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.controller.impl;

import com.sosu.rest.backend.controller.ProductCommentController;
import com.sosu.rest.backend.core.annotations.SoSuValidated;
import com.sosu.rest.backend.model.request.ProductCommentRequest;
import com.sosu.rest.backend.service.product.comment.ProductCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Locale;

@RestController
@Slf4j
@RequestMapping("/comment")
public class ProductCommentControllerImpl implements ProductCommentController {

    @Autowired
    private ProductCommentService productCommentService;

    /**
     * Product comment service
     *
     * @param request   of product comment
     * @param productId of product
     * @param locale    of user
     * @return void
     */
    @Override
    @SoSuValidated
    public ResponseEntity<Void> getProductByCategory(@Valid ProductCommentRequest request, Long productId, Locale locale) {
        productCommentService.addNewComment(productId, locale, request);
        return ResponseEntity.noContent().build();
    }
}
