package com.sosu.rest.crown.service;

import com.sosu.rest.crown.entity.postgres.Game;
import com.sosu.rest.crown.model.CommonProductModel;
import com.sosu.rest.crown.model.ProductByCategorySearchRequest;

import java.util.List;

public interface GamesService {

    Game getFromName(String name);

    void saveOrUpdate(Game game);

    List<CommonProductModel> getProductByCategory(ProductByCategorySearchRequest request);

    List<CommonProductModel> findRandomGame(Integer page);
}
