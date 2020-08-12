/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.service;

import com.sosu.rest.crown.entity.postgres.Product;
import com.sosu.rest.crown.model.CommonProductModel;
import com.sosu.rest.crown.model.ProductByCategorySearchRequest;

import java.util.List;

public interface ProductService {

    List<CommonProductModel> getProductByCategory(ProductByCategorySearchRequest request);

    void saveOrUpdate(Product product);

    List<CommonProductModel> findRandomProduct(Integer page);
}
