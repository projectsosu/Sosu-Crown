/**
 * @author : Oguz Kahraman
 * @since : 13.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.service;

import com.sosu.rest.crown.core.exception.SoSuException;
import com.sosu.rest.crown.entity.postgres.Game;
import com.sosu.rest.crown.mapper.CommonProductMapper;
import com.sosu.rest.crown.model.CommonProductDTO;
import com.sosu.rest.crown.model.CommonProductDetailDTO;
import com.sosu.rest.crown.model.ProductByCategorySearchRequest;
import com.sosu.rest.crown.repo.postgres.GameRepository;
import com.sosu.rest.crown.service.product.impl.GamesServiceImpl;
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
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GamesServiceTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private CommonProductMapper commonProductMapper;

    @InjectMocks
    private GamesServiceImpl gamesService;

    @Test
    void saveOrUpdate() {
        gamesService.saveOrUpdate(new Game());
        verify(gameRepository, times(1)).save(any());
    }

    @Test
    void getProductByCategory() {
        ProductByCategorySearchRequest productByCategorySearchRequest = new ProductByCategorySearchRequest();
        productByCategorySearchRequest.setDesc(true);
        when(commonProductMapper.gamesToCommon(anyList(), any())).thenReturn(Collections.nCopies(5, new CommonProductDTO()));
        List<CommonProductDTO> commonProductDTOS = gamesService.getProductByCategory(productByCategorySearchRequest);
        assertEquals(5, Objects.requireNonNull(commonProductDTOS).size());
    }

    @Test
    void getProductByCategoryNonDesc() {
        ProductByCategorySearchRequest productByCategorySearchRequest = new ProductByCategorySearchRequest();
        productByCategorySearchRequest.setDesc(false);
        when(commonProductMapper.gamesToCommon(anyList(), any())).thenReturn(Collections.nCopies(5, new CommonProductDTO()));
        List<CommonProductDTO> commonProductDTOS = gamesService.getProductByCategory(productByCategorySearchRequest);
        assertEquals(5, Objects.requireNonNull(commonProductDTOS).size());
    }

    @Test
    void findRandomGame() {
        when(gameRepository.findRandomGame(any())).thenReturn(new ArrayList<>());
        when(commonProductMapper.gamesToCommon(anyList(), any())).thenReturn(Collections.nCopies(5, new CommonProductDTO()));
        List<CommonProductDTO> commonProductDTOS = gamesService.findRandomGame(1);
        assertEquals(5, Objects.requireNonNull(commonProductDTOS).size());
    }

    @Test
    void findGame() {
        CommonProductDetailDTO commonProductDTO = new CommonProductDetailDTO();
        commonProductDTO.setName("example");
        when(gameRepository.findById(any())).thenReturn(Optional.of(new Game()));
        when(commonProductMapper.gameToCommon(any())).thenReturn(commonProductDTO);
        CommonProductDetailDTO commonProductModels = gamesService.findGame(1L);
        assertEquals("example", commonProductModels.getName());
    }

    @Test
    void findGameError() {
        when(gameRepository.findById(any())).thenReturn(Optional.empty());
        SoSuException exception = assertThrows(SoSuException.class, () -> gamesService.findGame(1L));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Product can not find", exception.getReason());
        assertEquals("PRODUCT_NOT_FOUND", exception.getCause().getMessage());
    }

    @Test
    void save() {
        gamesService.saveOrUpdate(new Game());
        verify(gameRepository, times(1)).save(any());
    }

}