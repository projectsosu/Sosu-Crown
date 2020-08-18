/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.service.product;

import com.sosu.rest.crown.entity.postgres.Game;
import com.sosu.rest.crown.model.CommonProductModel;
import com.sosu.rest.crown.model.ProductByCategorySearchRequest;

import java.util.List;

public interface GamesService {

    void saveOrUpdate(Game game);

    List<CommonProductModel> getProductByCategory(ProductByCategorySearchRequest request);

    List<CommonProductModel> findRandomGame(Integer page);

    CommonProductModel findGame(Long id);
}
