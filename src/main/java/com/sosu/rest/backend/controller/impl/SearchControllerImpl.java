/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.controller.impl;

import com.sosu.rest.backend.controller.SearchController;
import com.sosu.rest.backend.model.SearchResponseDTO;
import com.sosu.rest.backend.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/search")
public class SearchControllerImpl implements SearchController {

    @Autowired
    private SearchService searchService;

    /**
     * Search product by name
     *
     * @param name of product or game
     * @return foundsss products
     */
    @Override
    public ResponseEntity<List<SearchResponseDTO>> searchByName(String name) {
        return ResponseEntity.ok(searchService.searchByName(name));
    }
}
