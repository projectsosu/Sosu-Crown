/**
 * @author : Oguz Kahraman
 * @since : 18.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.service.product.comment.impl;

import com.sosu.rest.backend.entity.postgres.Game;
import com.sosu.rest.backend.repo.postgres.GameRepository;
import com.sosu.rest.backend.repo.postgres.ProductRepository;
import com.sosu.rest.backend.core.exception.SoSuException;
import com.sosu.rest.backend.core.util.LanguageUtil;
import com.sosu.rest.backend.entity.postgres.GameComment;
import com.sosu.rest.backend.entity.postgres.Product;
import com.sosu.rest.backend.entity.postgres.ProductComment;
import com.sosu.rest.backend.enums.ProductType;
import com.sosu.rest.backend.mapper.ProductCommentMapper;
import com.sosu.rest.backend.model.request.ProductCommentRequest;
import com.sosu.rest.backend.repo.postgres.GameCommentRepository;
import com.sosu.rest.backend.repo.postgres.ProductCommentRepository;
import com.sosu.rest.backend.service.product.comment.ProductCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Locale;

@Service
@Transactional
public class ProductCommentServiceImpl implements ProductCommentService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private ProductCommentRepository productCommentRepository;

    @Autowired
    private GameCommentRepository gameCommentRepository;

    @Autowired
    private ProductCommentMapper productCommentMapper;

    @Override
    public void addNewComment(Long productId, Locale locale, ProductCommentRequest productCommentRequest) {
        if (ProductType.PRODUCT.equals(productCommentRequest.getType())) {
            Product product = productRepository.findById(productId).
                    orElseThrow(() -> new SoSuException(HttpStatus.BAD_REQUEST,
                            "Product couldn't find", "PRODUCT_NOT_FOUND"));
            ProductComment productComment = productCommentMapper.mapDtoToEntity(productCommentRequest);
            productComment.setProduct(product);
            productComment.setLang(LanguageUtil.getLanguage(locale));
            productCommentRepository.save(productComment);
        } else {
            Game game = gameRepository.findById(productId).
                    orElseThrow(() -> new SoSuException(HttpStatus.BAD_REQUEST,
                            "Product couldn't find", "PRODUCT_NOT_FOUND"));
            GameComment productComment = productCommentMapper.mapGameDtoToEntity(productCommentRequest);
            productComment.setGame(game);
            productComment.setLang(LanguageUtil.getLanguage(locale));
            gameCommentRepository.save(productComment);
        }
    }

}