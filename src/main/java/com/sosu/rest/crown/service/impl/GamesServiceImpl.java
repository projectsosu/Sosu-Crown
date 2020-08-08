package com.sosu.rest.crown.service.impl;

import com.sosu.rest.crown.entity.postgres.Game;
import com.sosu.rest.crown.mapper.CommonProductMapper;
import com.sosu.rest.crown.model.CommonProductModel;
import com.sosu.rest.crown.model.ProductByCategorySearchRequest;
import com.sosu.rest.crown.repo.postgres.GameRepository;
import com.sosu.rest.crown.service.GamesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class GamesServiceImpl implements GamesService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private CommonProductMapper commonProductMapper;

    @Override
    public void saveOrUpdate(Game game) {
        gameRepository.save(game);
    }

    @Override
    public List<CommonProductModel> getProductByCategory(ProductByCategorySearchRequest request) {
        if (request.getDesc()) {
            return commonProductMapper.gamesToCommon(gameRepository.findByCategoryId(request.getCategoryId(), PageRequest.of(request.getPage() - 1, request.getPageSize(),
                    Sort.by(request.getSortBy().label).descending())));
        } else {
            return commonProductMapper.gamesToCommon(gameRepository.findByCategoryId(request.getCategoryId(), PageRequest.of(request.getPage() - 1, request.getPageSize(),
                    Sort.by(request.getSortBy().label).ascending())));
        }
    }

    @Override
    public List<CommonProductModel> findRandomGame(Integer page) {
        return commonProductMapper.gamesToCommon(gameRepository.findRandomGame(PageRequest.of(page, 10, Sort.by("name"))));
    }
}
