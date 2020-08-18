/**
 * @author : Oguz Kahraman
 * @since : 13.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.service;
import com.sosu.rest.crown.enums.ProductType;

import com.sosu.rest.crown.mapper.GamesMapper;
import com.sosu.rest.crown.mapper.ProductMapper;
import com.sosu.rest.crown.model.SearchResponseDTO;
import com.sosu.rest.crown.repo.postgres.GameRepository;
import com.sosu.rest.crown.repo.postgres.ProductRepository;
import com.sosu.rest.crown.service.impl.SearchServiceImpl;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private GamesMapper gamesMapper;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private SearchServiceImpl searchService;

    @Test
    void searchByName() {
        when(gameRepository.findTop10ByAndNameContains(any())).thenReturn(new ArrayList<>());
        when(productRepository.findTop10ByAndNameContains(any())).thenReturn(new ArrayList<>());
        when(gamesMapper.entityToModel(any())).thenReturn(Collections.singletonList(getResponseModel()));
        when(productMapper.entityToModel(any())).thenReturn(Collections.singletonList(getResponseModel()));
        List<SearchResponseDTO> searchResponseDTOS = searchService.searchByName("");
        assertEquals(2, Objects.requireNonNull(searchResponseDTOS).size());
    }

    @Test
    void searchByNameMany() {
        when(gameRepository.findTop10ByAndNameContains(any())).thenReturn(new ArrayList<>());
        when(productRepository.findTop10ByAndNameContains(any())).thenReturn(new ArrayList<>());
        when(gamesMapper.entityToModel(any())).thenReturn(Collections.nCopies(10, getResponseModel()));
        when(productMapper.entityToModel(any())).thenReturn(Collections.nCopies(10, getResponseModel()));
        List<SearchResponseDTO> searchResponseDTOS = searchService.searchByName("");
        assertEquals(10, Objects.requireNonNull(searchResponseDTOS).size());
    }

    private SearchResponseDTO getResponseModel(){
        SearchResponseDTO searchResponseDTO = new SearchResponseDTO();
        searchResponseDTO.setName("asdadasd");
        searchResponseDTO.setId(0L);
        searchResponseDTO.setProductType(ProductType.GAME);
        return searchResponseDTO;
    }

}