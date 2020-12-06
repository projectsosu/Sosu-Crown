/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.service;

import com.sosu.rest.backend.model.SearchResponseDTO;

import java.util.List;

public interface SearchService {

    List<SearchResponseDTO> searchByName(String name);

}
