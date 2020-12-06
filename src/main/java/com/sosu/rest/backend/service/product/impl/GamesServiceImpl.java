/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.service.product.impl;

import com.sosu.rest.backend.entity.postgres.Game;
import com.sosu.rest.backend.mapper.CommonProductMapper;
import com.sosu.rest.backend.repo.postgres.GameRepository;
import com.sosu.rest.backend.core.exception.SoSuException;
import com.sosu.rest.backend.model.CommonProductDetailDTO;
import com.sosu.rest.backend.model.CommonProductDTO;
import com.sosu.rest.backend.model.request.ProductByCategorySearchRequest;
import com.sosu.rest.backend.service.CategoryService;
import com.sosu.rest.backend.service.product.GamesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Game processes
 */
@Service
@Transactional
public class GamesServiceImpl implements GamesService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private CommonProductMapper commonProductMapper;

    /**
     * Saves game
     *
     * @param game game entity
     */
    @Override
    public void saveOrUpdate(Game game) {
        gameRepository.save(game);
    }

    /**
     * Get games by selected conditions
     *
     * @param request search params
     * @return found game list
     */
    @Override
    public List<CommonProductDTO> getProductByCategory(ProductByCategorySearchRequest request) {
        if (request.getDesc()) {
            return commonProductMapper.gamesToCommon(gameRepository.findByCategoryId(request.getCategoryId(), PageRequest.of(request.getPage() - 1, request.getPageSize(),
                    Sort.by(request.getSortBy().label).descending())), categoryService);
        } else {
            return commonProductMapper.gamesToCommon(gameRepository.findByCategoryId(request.getCategoryId(), PageRequest.of(request.getPage() - 1, request.getPageSize(),
                    Sort.by(request.getSortBy().label).ascending())), categoryService);
        }
    }

    /**
     * Gets random games for initial page
     *
     * @param page page number
     * @return random 10 games
     */
    @Override
    public List<CommonProductDTO> findRandomGame(Integer page) {
        return commonProductMapper.gamesToCommon(gameRepository.findRandomGame(PageRequest.of(page, 10, Sort.by("name"))), categoryService);
    }

    /**
     * Get game detail
     *
     * @param id of game
     * @return of game detail
     */
    @Override
    public CommonProductDetailDTO findGame(Long id) {
        return commonProductMapper.gameToCommon(gameRepository.findById(id).orElseThrow(() ->
                new SoSuException(HttpStatus.BAD_REQUEST, "Product can not find", "PRODUCT_NOT_FOUND")
        ), categoryService);
    }
}
