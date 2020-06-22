package com.sosu.rest.crown.service;

import com.sosu.rest.crown.entity.mongo.Category;

public interface CategoryService {

    Category saveOrUpdate(Category category);

    Category findByNameAndLang(String name, String lang);

}
