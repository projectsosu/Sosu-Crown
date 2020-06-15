package com.sosu.rest.crown.service.impl;

import com.sosu.rest.crown.entitiy.Product;
import com.sosu.rest.crown.repo.ProductRepository;
import com.sosu.rest.crown.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;

    @Override
    public Product getProductByNameAndYear(String name, int year) {
        return repository.getProductByNameAndYear(name, year);
    }

    @Override
    @Transactional
    public void saveOrUpdate(Product product) {
        repository.save(product);
    }
}
