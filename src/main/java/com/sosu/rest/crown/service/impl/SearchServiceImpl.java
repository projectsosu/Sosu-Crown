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
import com.sosu.rest.crown.model.SearchResponseModel;
import com.sosu.rest.crown.repo.postgres.GameRepository;
import com.sosu.rest.crown.repo.postgres.ProductRepository;
import com.sosu.rest.crown.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private GamesMapper gamesMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<SearchResponseModel> searchByName(String name) {
        List<Game> games = gameRepository.findTop10ByAndNameContains(name);
        List<Product> products = productRepository.findTop10ByAndNameContains(name);
        List<SearchResponseModel> searchResponseModels = new ArrayList<>();
        searchResponseModels.addAll(gamesMapper.entityToModel(games));
        searchResponseModels.addAll(productMapper.entityToModel(products));
        searchResponseModels.sort(Comparator.comparing(SearchResponseModel::getName));
        if (searchResponseModels.size() > 10) {
            return searchResponseModels.subList(0, 10);
        }
        return searchResponseModels;
    }
}
