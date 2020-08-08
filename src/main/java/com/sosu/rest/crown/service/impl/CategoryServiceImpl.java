package com.sosu.rest.crown.service.impl;

import com.sosu.rest.crown.entity.mongo.Category;
import com.sosu.rest.crown.enums.ProductType;
import com.sosu.rest.crown.mapper.CategoryMapper;
import com.sosu.rest.crown.model.CategoryDTO;
import com.sosu.rest.crown.repo.mongo.CategoryRepository;
import com.sosu.rest.crown.repo.postgres.GameRepository;
import com.sosu.rest.crown.repo.postgres.ProductRepository;
import com.sosu.rest.crown.service.CategoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private GameRepository gameRepository;

    @Override
    public Category saveOrUpdate(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category findByNameAndLang(String name, String lang) {
        return categoryRepository.findByNameAndLang(name, lang);
    }

    @Override
    @Cacheable("categoriesById")
    public List<CategoryDTO> findByParentId(String categoryId) {
        List<Category> categories = categoryRepository.findByParentId(categoryId, Sort.by(Sort.Direction.ASC, "id"));
        return getCategoryDTOS(categories);
    }

    @Override
    @Cacheable("categories")
    public List<CategoryDTO> getCategoryList(String lang) {
        List<Category> categories = categoryRepository.findByLang(StringUtils.isEmpty(lang) ? "en_US" : lang);
        return getCategoryDTOS(categories);
    }

    private List<CategoryDTO> getCategoryDTOS(List<Category> categories) {
        List<CategoryDTO> categoryDTOS = categoryMapper.entityToModel(categories);
        categoryDTOS.parallelStream().forEach(item -> {
            if (item.getType().equals(ProductType.GAME)) {
                if (item.getParentId() == null) {
                    item.setItemCount(gameRepository.countByMainCategoryIdContaining(item.getDefaultCategory()));
                } else if (item.getConsole() != null && item.getConsole()) {
                    item.setItemCount(gameRepository.countByConsoleCategoryIdContaining(item.getDefaultCategory()));
                } else {
                    item.setItemCount(gameRepository.countByCategoryIdContaining(item.getDefaultCategory()));
                }
            } else {
                if (item.getParentId() == null) {
                    item.setItemCount(productRepository.countByMainCategoryIdContaining(item.getDefaultCategory()));
                } else {
                    item.setItemCount(productRepository.countByCategoryIdContaining(item.getDefaultCategory()));
                }
            }
        });
        return categoryDTOS;
    }

}
