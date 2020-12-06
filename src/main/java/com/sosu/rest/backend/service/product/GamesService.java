/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.service.product;

import com.sosu.rest.backend.entity.postgres.Game;
import com.sosu.rest.backend.model.CommonProductDetailDTO;
import com.sosu.rest.backend.model.CommonProductDTO;
import com.sosu.rest.backend.model.request.ProductByCategorySearchRequest;

import java.util.List;

public interface GamesService {

    void saveOrUpdate(Game game);

    List<CommonProductDTO> getProductByCategory(ProductByCategorySearchRequest request);

    List<CommonProductDTO> findRandomGame(Integer page);

    CommonProductDetailDTO findGame(Long id);
}
