package com.sosu.rest.crown.service;

import com.sosu.rest.crown.entity.postgres.Product;
import com.sosu.rest.crown.model.ProductByCategorySearchRequest;

import java.util.List;

public interface ProductService {

    Product getProductByNameAndYear(String name, int year);

    Product getProductByImdbId(String imdbId);

    List<Product> getProductByCategory(ProductByCategorySearchRequest request);

    void saveOrUpdate(Product product);

    List<Product> getAll();

    List<Product> getNotUploaded();

}
