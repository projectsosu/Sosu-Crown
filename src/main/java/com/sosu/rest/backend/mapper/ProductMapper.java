/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.mapper;

import com.sosu.rest.backend.entity.postgres.Product;
import com.sosu.rest.backend.enums.ProductType;
import com.sosu.rest.backend.model.SearchResponseDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    List<SearchResponseDTO> entityToModel(List<Product> products);

    @AfterMapping
    default void afterMap(@MappingTarget SearchResponseDTO searchResponseDTO) {
        searchResponseDTO.setProductType(ProductType.PRODUCT);
    }

}
