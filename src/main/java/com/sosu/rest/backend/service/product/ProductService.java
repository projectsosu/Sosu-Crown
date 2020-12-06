/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.service.product;

import com.sosu.rest.backend.entity.postgres.Product;
import com.sosu.rest.backend.model.CommonProductDetailDTO;
import com.sosu.rest.backend.model.CommonProductDTO;
import com.sosu.rest.backend.model.request.ProductByCategorySearchRequest;

import java.util.List;

public interface ProductService {

    List<CommonProductDTO> getProductByCategory(ProductByCategorySearchRequest request);

    void saveOrUpdate(Product product);

    List<CommonProductDTO> findRandomProduct(Integer page);

    CommonProductDetailDTO findProduct(Long id);
}
