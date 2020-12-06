/**
 * @author : Oguz Kahraman
 * @since : 18.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.service.product.comment;

import com.sosu.rest.backend.model.request.ProductCommentRequest;

import java.util.Locale;

public interface ProductCommentService {

    void addNewComment(Long productId, Locale locale, ProductCommentRequest productCommentRequest);

}