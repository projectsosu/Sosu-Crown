package com.sosu.rest.crown.controller.impl;

import com.sosu.rest.crown.controller.SearchController;
import com.sosu.rest.crown.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/search")
public class SearchControllerImpl implements SearchController {

    @Autowired
    private SearchService searchService;

    @Override
    public ResponseEntity searchByName(String name) {
        return ResponseEntity.ok(searchService.searchByName(name));
    }
}
