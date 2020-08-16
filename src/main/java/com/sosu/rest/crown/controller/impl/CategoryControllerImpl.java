/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.controller.impl;

import com.sosu.rest.crown.controller.CategoryController;
import com.sosu.rest.crown.core.util.LanguageUtil;
import com.sosu.rest.crown.model.CategoryDTO;
import com.sosu.rest.crown.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/category")
public class CategoryControllerImpl implements CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * Getting categories
     *
     * @param locale language of user
     * @return category list
     */
    @Override
    public ResponseEntity<List<CategoryDTO>> getCategoryList(Locale locale) {
        return ResponseEntity.ok(categoryService.getCategoryList(LanguageUtil.getLanguage(locale)));
    }

    /**
     * Getting child categories
     *
     * @param categoryId parent category id
     * @return child category list
     */
    @Override
    public ResponseEntity<List<CategoryDTO>> findByParentId(String categoryId) {
        return ResponseEntity.ok(categoryService.findByParentId(categoryId));
    }

}
