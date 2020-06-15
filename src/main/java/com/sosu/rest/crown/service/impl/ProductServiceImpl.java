package com.sosu.rest.crown.service.impl;

import com.sosu.rest.crown.entitiy.Product;
import com.sosu.rest.crown.repo.postgres.ProductRepository;
import com.sosu.rest.crown.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;

    @Override
    public Product getProductByNameAndYear(String name, int year) {
        return repository.getProductByNameAndYear(name, year);
    }

    @Override
    public List<Product> getProductByCategory(String category, int limit, String sortBy, boolean desc) {
        if (desc) {
            return repository.getProductByCategory(category, PageRequest.of(0, limit, Sort.by(sortBy).descending()));
        } else {
            return repository.getProductByCategory(category, PageRequest.of(0, limit, Sort.by(sortBy).ascending()));
        }
    }

    @Override
    @Transactional
    public void saveOrUpdate(Product product) {
        repository.save(product);
    }
}
