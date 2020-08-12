/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.service;

import com.sosu.rest.crown.model.SearchResponseModel;

import java.util.List;

public interface SearchService {

    List<SearchResponseModel> searchByName(String name);

}
