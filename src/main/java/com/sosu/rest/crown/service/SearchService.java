package com.sosu.rest.crown.service;

import com.sosu.rest.crown.model.SearchResponseModel;

import java.util.List;

public interface SearchService {

    List<SearchResponseModel> searchByName(String name);

}
