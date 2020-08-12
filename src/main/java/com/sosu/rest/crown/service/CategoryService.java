/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.service;

import com.sosu.rest.crown.entity.mongo.Category;
import com.sosu.rest.crown.model.CategoryDTO;

import java.util.List;

public interface CategoryService {

    Category saveOrUpdate(Category category);

    Category findByNameAndLang(String name, String lang);

    List<CategoryDTO> findByParentId(String categoryId);

    List<CategoryDTO> getCategoryList(String lang);
}
