/**
 * @author : Oguz Kahraman
 * @since : 13.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.service;

import com.sosu.rest.backend.entity.mongo.Category;
import com.sosu.rest.backend.enums.ProductType;
import com.sosu.rest.backend.mapper.CategoryMapper;
import com.sosu.rest.backend.model.CategoryDTO;
import com.sosu.rest.backend.repo.mongo.CategoryRepository;
import com.sosu.rest.backend.repo.postgres.GameRepository;
import com.sosu.rest.backend.repo.postgres.ProductRepository;
import com.sosu.rest.backend.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private ConcurrentMap<String, String> categoryMap;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @AfterEach
    void cleanTests() {
        reset(gameRepository, categoryRepository, categoryMapper, productRepository, categoryMap);
    }

    @Test
    void findByParentId() {
        when(categoryMapper.entityListToModelList(any())).thenReturn(Collections.singletonList(new CategoryDTO()));
        when(categoryRepository.findByParentId(any(), any())).thenReturn(Collections.singletonList(new Category()));
        List<CategoryDTO> categoryDTOS = categoryService.findByParentId("");
        assertEquals(1, Objects.requireNonNull(categoryDTOS).size());
    }

    @Test
    void findByParentIdProductParent() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setParentId("weqeqwe");
        when(categoryMapper.entityListToModelList(any())).thenReturn(Collections.singletonList(categoryDTO));
        when(categoryRepository.findByParentId(any(), any())).thenReturn(Collections.singletonList(new Category()));
        List<CategoryDTO> categoryDTOS = categoryService.findByParentId("");
        assertEquals(1, Objects.requireNonNull(categoryDTOS).size());
    }

    @Test
    void findByParentIdGame() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setType(ProductType.GAME);
        when(categoryMapper.entityListToModelList(any())).thenReturn(Collections.singletonList(categoryDTO));
        when(categoryRepository.findByParentId(any(), any())).thenReturn(Collections.singletonList(new Category()));
        List<CategoryDTO> categoryDTOS = categoryService.findByParentId("");
        assertEquals(1, Objects.requireNonNull(categoryDTOS).size());
    }

    @Test
    void findByParentIdGameParent() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setParentId("weqeqwe");
        categoryDTO.setType(ProductType.GAME);
        when(categoryMapper.entityListToModelList(any())).thenReturn(Collections.singletonList(categoryDTO));
        when(categoryRepository.findByParentId(any(), any())).thenReturn(Collections.singletonList(new Category()));
        List<CategoryDTO> categoryDTOS = categoryService.findByParentId("");
        assertEquals(1, Objects.requireNonNull(categoryDTOS).size());
    }

    @Test
    void getCategoryList() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setParentId("weqeqwe");
        categoryDTO.setType(ProductType.GAME);
        categoryDTO.setConsole(true);
        when(categoryMapper.entityListToModelList(any())).thenReturn(Collections.singletonList(categoryDTO));
        when(categoryRepository.findByLang(any())).thenReturn(Collections.singletonList(new Category()));
        List<CategoryDTO> categoryDTOS = categoryService.getCategoryList("");
        assertEquals(1, Objects.requireNonNull(categoryDTOS).size());
    }

    @Test
    void getCategoryName() {
        when(categoryMap.get("any")).thenReturn("found");
        String name = categoryService.getCategoryName("any");
        assertEquals("found", name);
    }

    @Test
    void getCategoryNameNull() {
        when(categoryMap.get("any")).thenReturn(null);
        String name = categoryService.getCategoryName("any");
        assertNull(name);
    }
}