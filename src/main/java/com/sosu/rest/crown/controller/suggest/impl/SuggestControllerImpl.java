/**
 * @author : Oguz Kahraman
 * @since : 19.09.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.controller.suggest.impl;

import com.sosu.rest.crown.controller.suggest.SuggestController;
import com.sosu.rest.crown.core.annotations.SoSuValidated;
import com.sosu.rest.crown.model.SuggestDTO;
import com.sosu.rest.crown.model.request.NewSuggestRequest;
import com.sosu.rest.crown.service.suggest.SuggestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

@RestController
@RequestMapping("/suggest")
public class SuggestControllerImpl implements SuggestController {

    @Autowired
    private SuggestService suggestService;

    /**
     * Adds new suggest for user
     *
     * @param newSuggestRequest request information
     * @param locale            user locale
     * @return no content
     */
    @Override
    @SoSuValidated
    public ResponseEntity<Void> addNewSuggest(NewSuggestRequest newSuggestRequest, Locale locale) {
        suggestService.addNewSuggest(newSuggestRequest.getUserName(), newSuggestRequest.getComment(), locale);
        return ResponseEntity.noContent().build();
    }

    @Override
    @SoSuValidated
    public ResponseEntity<List<SuggestDTO>> getUserSuggests(String userName, Integer page) {
        return ResponseEntity.ok(suggestService.findUserSuggests(userName, Objects.requireNonNullElse(page, 0)));
    }
}
