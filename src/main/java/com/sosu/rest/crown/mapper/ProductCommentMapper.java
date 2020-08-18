/**
 * @author : Oguz Kahraman
 * @since : 18.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.mapper;

import com.sosu.rest.crown.entity.postgres.GameComment;
import com.sosu.rest.crown.entity.postgres.ProductComment;
import com.sosu.rest.crown.model.ProductCommentRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductCommentMapper {

    ProductComment mapDtoToEntity(ProductCommentRequest productCommentRequest);

    GameComment mapGameDtoToEntity(ProductCommentRequest productCommentRequest);

}
