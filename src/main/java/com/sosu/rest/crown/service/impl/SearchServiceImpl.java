/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.service.impl;

import com.sosu.rest.crown.entity.postgres.Game;
import com.sosu.rest.crown.entity.postgres.Product;
import com.sosu.rest.crown.mapper.GamesMapper;
import com.sosu.rest.crown.mapper.ProductMapper;
import com.sosu.rest.crown.model.SearchResponseDTO;
import com.sosu.rest.crown.repo.postgres.GameRepository;
import com.sosu.rest.crown.repo.postgres.ProductRepository;
import com.sosu.rest.crown.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * General product and game searcher
 */
@Service
@Transactional
public class SearchServiceImpl implements SearchService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private GamesMapper gamesMapper;

    @Autowired
    private ProductMapper productMapper;

    /**
     * Search like name
     *
     * @param name search request
     * @return found game and product list
     */
    @Override
    public List<SearchResponseDTO> searchByName(String name) {
        List<Game> games = gameRepository.findTop10ByAndNameContains(name);
        List<Product> products = productRepository.findTop10ByAndNameContains(name);
        List<SearchResponseDTO> searchResponseDTOS = new ArrayList<>();
        searchResponseDTOS.addAll(gamesMapper.entityToModel(games));
        searchResponseDTOS.addAll(productMapper.entityToModel(products));
        searchResponseDTOS.sort(Comparator.comparing(SearchResponseDTO::getName));
        if (searchResponseDTOS.size() > 10) {
            return searchResponseDTOS.subList(0, 10);
        }
        return searchResponseDTOS;
    }
}
