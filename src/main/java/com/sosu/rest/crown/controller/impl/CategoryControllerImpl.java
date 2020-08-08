package com.sosu.rest.crown.controller.impl;

import com.sosu.rest.crown.controller.CategoryController;
import com.sosu.rest.crown.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryControllerImpl implements CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Override
    public ResponseEntity getCategoryList(String lang) {
        return ResponseEntity.ok(categoryService.getCategoryList(lang));
    }

}
