/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.service;

import com.sosu.rest.crown.model.CategoryDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> findByParentId(String categoryId);

    List<CategoryDTO> getCategoryList(String lang);

    String getCategoryName(String id);
}
