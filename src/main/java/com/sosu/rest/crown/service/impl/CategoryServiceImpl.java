/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
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

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * This service includes category processes
 */
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

    private ConcurrentMap<String, String> categoryMap = new ConcurrentHashMap<>();

    @PostConstruct
    @Cacheable("categories")
    public List<CategoryDTO> createHashedCategories() {
        List<Category> categories = categoryRepository.findByLang("en_US");
        createHashMap(categories);
        return getCategoryDTOS(categories);
    }

    /**
     * Get category list from parent id
     *
     * @param categoryId parent id of category
     * @return cached category list
     */
    @Override
    @Cacheable("categoriesById")
    public List<CategoryDTO> findByParentId(String categoryId) {
        List<Category> categories = categoryRepository.findByParentId(categoryId, Sort.by(Sort.Direction.ASC, "id"));
        return getCategoryDTOS(categories);
    }

    /**
     * Get category list by language
     *
     * @param lang language of user
     * @return cached category list by language
     */
    @Override
    @Cacheable("categories")
    public List<CategoryDTO> getCategoryList(String lang) {
        List<Category> categories = categoryRepository.findByLang(StringUtils.isEmpty(lang) ? "en_US" : lang);
        return getCategoryDTOS(categories);
    }

    /**
     * Get category name by language
     *
     * @param id   of category
     * @return category anme
     */
    @Override
    public String getCategoryName(String id) {
        return categoryMap.get(id);
    }

    private List<CategoryDTO> getCategoryDTOS(List<Category> categories) {
        List<CategoryDTO> categoryDTOS = categoryMapper.entityToModel(categories);
        categoryDTOS.parallelStream().forEach(item -> {
            if (ProductType.GAME.equals(item.getType())) {
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

    private synchronized void createHashMap(List<Category> categories) {
        categoryMap.clear();
        categories.parallelStream().forEach(item -> categoryMap.put(item.getId(), item.getName()));
    }

}
