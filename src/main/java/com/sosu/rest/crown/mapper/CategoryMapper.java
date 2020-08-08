package com.sosu.rest.crown.mapper;

import com.sosu.rest.crown.entity.mongo.Category;
import com.sosu.rest.crown.entity.postgres.Product;
import com.sosu.rest.crown.enums.ProductType;
import com.sosu.rest.crown.model.CategoryDTO;
import com.sosu.rest.crown.model.SearchResponseModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    List<CategoryDTO> entityToModel(List<Category> products);

}
