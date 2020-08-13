/**
 * @author : Oguz Kahraman
 * @since : 13.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.service;

import com.sosu.rest.crown.entity.postgres.Game;
import com.sosu.rest.crown.mapper.CommonProductMapper;
import com.sosu.rest.crown.model.CommonProductModel;
import com.sosu.rest.crown.model.ProductByCategorySearchRequest;
import com.sosu.rest.crown.repo.postgres.GameRepository;
import com.sosu.rest.crown.service.impl.GamesServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        when(commonProductMapper.gamesToCommon(anyList(), any())).thenReturn(Collections.nCopies(5, new CommonProductModel()));
        List<CommonProductModel> commonProductModels = gamesService.getProductByCategory(productByCategorySearchRequest);
        assertEquals(5, Objects.requireNonNull(commonProductModels).size());
    }

    @Test
    void getProductByCategoryNonDesc() {
        ProductByCategorySearchRequest productByCategorySearchRequest = new ProductByCategorySearchRequest();
        productByCategorySearchRequest.setDesc(false);
        when(commonProductMapper.gamesToCommon(anyList(), any())).thenReturn(Collections.nCopies(5, new CommonProductModel()));
        List<CommonProductModel> commonProductModels = gamesService.getProductByCategory(productByCategorySearchRequest);
        assertEquals(5, Objects.requireNonNull(commonProductModels).size());
    }

    @Test
    void findRandomGame() {
        when(gameRepository.findRandomGame(any())).thenReturn(new ArrayList<>());
        when(commonProductMapper.gamesToCommon(anyList(), any())).thenReturn(Collections.nCopies(5, new CommonProductModel()));
        List<CommonProductModel> commonProductModels = gamesService.findRandomGame(1);
        assertEquals(5, Objects.requireNonNull(commonProductModels).size());
    }
}