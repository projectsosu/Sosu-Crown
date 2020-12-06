/**
 * @author : Oguz Kahraman
 * @since : 19.09.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.mapper;

import com.sosu.rest.backend.model.suggest.SuggestCommentDTO;
import com.sosu.rest.backend.entity.postgres.Suggest;
import com.sosu.rest.backend.entity.postgres.SuggestComment;
import com.sosu.rest.backend.model.suggest.SuggestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SuggestMapper {

    @Mapping(target = "suggest", source = "userSuggest")
    SuggestDTO entityToDto(Suggest suggest);

    List<SuggestDTO> entityListToDtoList(List<Suggest> suggest);

    SuggestCommentDTO commentEntityToDto(SuggestComment suggest);

    List<SuggestCommentDTO> commentEntityListToDtoList(List<SuggestComment> suggest);

}
