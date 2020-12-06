/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.mapper;

import com.sosu.rest.backend.model.CategoryDTO;
import com.sosu.rest.backend.entity.mongo.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "categoryId", source = "id")
    CategoryDTO entityToModel(Category product);

    List<CategoryDTO> entityListToModelList(List<Category> products);

}
