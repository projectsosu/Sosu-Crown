/**
 * @author : Oguz Kahraman
 * @since : 19.09.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.mapper;

import com.sosu.rest.crown.entity.postgres.Suggest;
import com.sosu.rest.crown.model.SuggestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SuggestMapper {

    @Mapping(target = "suggest", source = "userSuggest")
    SuggestDTO entityToDto(Suggest suggest);

    List<SuggestDTO> entityListToDtoList(List<Suggest> suggest);

}
