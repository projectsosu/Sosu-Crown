/**
 * @author : Oguz Kahraman
 * @since : 18.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.service.product.comment;

import com.sosu.rest.crown.entity.postgres.Game;
import com.sosu.rest.crown.entity.postgres.GameComment;
import com.sosu.rest.crown.entity.postgres.Product;
import com.sosu.rest.crown.entity.postgres.ProductComment;
import com.sosu.rest.crown.enums.ProductType;
import com.sosu.rest.crown.mapper.ProductCommentMapper;
import com.sosu.rest.crown.model.ProductCommentRequest;
import com.sosu.rest.crown.repo.postgres.GameCommentRepository;
import com.sosu.rest.crown.repo.postgres.GameRepository;
import com.sosu.rest.crown.repo.postgres.ProductCommentRepository;
import com.sosu.rest.crown.repo.postgres.ProductRepository;
import com.sosu.rest.crown.service.product.comment.impl.ProductCommentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Locale;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductCommentServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private ProductCommentRepository productCommentRepository;

    @Mock
    private GameCommentRepository gameCommentRepository;

    @Mock
    private ProductCommentMapper productCommentMapper;

    @InjectMocks
    private ProductCommentServiceImpl productCommentService;

    @Test
    void addNewComment() {
        ProductCommentRequest productCommentRequest = new ProductCommentRequest();
        when(productCommentMapper.mapDtoToEntity(any())).thenReturn(mock(ProductComment.class));
        productCommentRequest.setType(ProductType.PRODUCT);
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(new Product()));
        productCommentService.addNewComment(1L, Locale.ENGLISH, productCommentRequest);
        verify(productCommentRepository, times(1)).save(any());
    }

    @Test
    void addNewCommentGame() {
        ProductCommentRequest productCommentRequest = new ProductCommentRequest();
        when(productCommentMapper.mapGameDtoToEntity(any())).thenReturn(mock(GameComment.class));
        productCommentRequest.setType(ProductType.GAME);
        when(gameRepository.findById(anyLong())).thenReturn(Optional.of(new Game()));
        productCommentService.addNewComment(1L, Locale.ENGLISH, productCommentRequest);
        verify(gameCommentRepository, times(1)).save(any());
    }
}