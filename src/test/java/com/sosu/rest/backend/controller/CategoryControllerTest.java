/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.controller;

import com.sosu.rest.backend.controller.impl.CategoryControllerImpl;
import com.sosu.rest.backend.model.CategoryDTO;
import com.sosu.rest.backend.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryControllerImpl categoryController;

    @Test
    void getCategoryList_empty() {
        when(categoryService.getCategoryList("en")).thenReturn(new ArrayList<>());
        ResponseEntity<List<CategoryDTO>> responseEntity = categoryController.getCategoryList(null);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void getCategoryList_null() {
        when(categoryService.getCategoryList("en")).thenReturn(null);
        ResponseEntity<List<CategoryDTO>> responseEntity = categoryController.getCategoryList(null);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void findByParentId() {
        when(categoryService.findByParentId("en")).thenReturn(new ArrayList<>());
        ResponseEntity<List<CategoryDTO>> responseEntity = categoryController.findByParentId("en");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void findByParentId_empty() {
        when(categoryService.findByParentId(null)).thenReturn(null);
        ResponseEntity<List<CategoryDTO>> responseEntity = categoryController.findByParentId(null);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

}