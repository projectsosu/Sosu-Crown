package com.sosu.rest.crown.mapper;

import com.sosu.rest.crown.entity.postgres.Product;
import com.sosu.rest.crown.enums.ProductType;
import com.sosu.rest.crown.model.SearchResponseModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    List<SearchResponseModel> entityToModel(List<Product> products);

    @AfterMapping
    default void afterMap(@MappingTarget SearchResponseModel searchResponseModel) {
        searchResponseModel.setProductType(ProductType.PRODUCT);
    }

}
