package com.sosu.rest.crown.service;

import com.sosu.rest.crown.entity.postgres.Product;

import java.util.List;

public interface ProductService {

    Product getProductByNameAndYear(String name, int year);

    List<Product> getProductByCategory(String category, int limit, String sortBy, boolean desc);

    void saveOrUpdate(Product product);

}
