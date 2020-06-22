package com.sosu.rest.crown.service.impl;

import com.sosu.rest.crown.entity.mongo.Category;
import com.sosu.rest.crown.repo.mongo.CategoryRepository;
import com.sosu.rest.crown.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category saveOrUpdate(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category findByNameAndLang(String name, String lang) {
        return categoryRepository.findByNameAndLang(name, lang);
    }

}
