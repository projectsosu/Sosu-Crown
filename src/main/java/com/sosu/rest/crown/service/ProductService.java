package com.sosu.rest.crown.service;

import com.sosu.rest.crown.entitiy.Product;

public interface ProductService {

    Product getProductByNameAndYear(String name, int year);

    void saveOrUpdate(Product product);

}
