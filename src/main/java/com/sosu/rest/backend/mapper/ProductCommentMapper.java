/**
 * @author : Oguz Kahraman
 * @since : 18.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.mapper;

import com.sosu.rest.backend.entity.postgres.GameComment;
import com.sosu.rest.backend.entity.postgres.ProductComment;
import com.sosu.rest.backend.model.request.ProductCommentRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductCommentMapper {

    ProductComment mapDtoToEntity(ProductCommentRequest productCommentRequest);

    GameComment mapGameDtoToEntity(ProductCommentRequest productCommentRequest);

}
