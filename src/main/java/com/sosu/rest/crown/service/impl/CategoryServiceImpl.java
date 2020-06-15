package com.sosu.rest.crown.service.impl;

import com.sosu.rest.crown.entity.mongo.Category;
import com.sosu.rest.crown.repo.mongo.CategoryRepository;
import com.sosu.rest.crown.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getProductByNameAndYear() {
        return (List<Category>) categoryRepository.findAll();
    }
}
