package com.sosu.rest.crown.service.impl;

import com.sosu.rest.crown.entity.postgres.Product;
import com.sosu.rest.crown.model.ProductByCategorySearchRequest;
import com.sosu.rest.crown.repo.postgres.ProductRepository;
import com.sosu.rest.crown.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;

    @Override
    public Product getProductByNameAndYear(String name, int year) {
        return repository.getProductByNameAndYear(name, year);
    }

    @Override
    public Product getProductByImdbId(String imdbId) {
        return repository.getProductByImdbId(imdbId);
    }

    @Override
    public List<Product> getProductByCategory(ProductByCategorySearchRequest request) {
        if (request.getDesc()) {
            return repository.getProductByCategory(request.getCategoryId(), PageRequest.of(request.getPage() - 1, request.getPageSize(),
                    Sort.by(request.getSortBy().label).descending()));
        } else {
            return repository.getProductByCategory(request.getCategoryId(), PageRequest.of(request.getPage() - 1, request.getPageSize(),
                    Sort.by(request.getSortBy().label).ascending()));
        }
    }

    @Override
    public void saveOrUpdate(Product product) {
        repository.save(product);
    }

    @Override
    public List<Product> getAll() {
        return (List<Product>) repository.findAll();
    }

    @Override
    public List<Product> getNotUploaded() {
        return repository.getNotUploaded();
    }
}
