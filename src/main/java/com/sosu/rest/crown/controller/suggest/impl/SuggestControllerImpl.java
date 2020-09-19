/**
 * @author : Oguz Kahraman
 * @since : 19.09.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.controller.suggest.impl;

import com.sosu.rest.crown.controller.suggest.SuggestController;
import com.sosu.rest.crown.core.annotations.SoSuValidated;
import com.sosu.rest.crown.core.util.SecurityCheckUtil;
import com.sosu.rest.crown.model.request.NewSuggestRequest;
import com.sosu.rest.crown.model.suggest.SuggestDTO;
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

    @Autowired
    private SecurityCheckUtil securityCheckUtil;

    /**
     * Adds new suggest for user
     *
     * @param newSuggestRequest request information
     * @param locale            user locale
     * @return no content
     */
    @Override
    @SoSuValidated
    public ResponseEntity<Void> addNewSuggest(NewSuggestRequest newSuggestRequest, String userName, String jwtToken, Locale locale) {
        securityCheckUtil.checkUserValidity(userName, jwtToken);
        suggestService.addNewSuggest(userName, newSuggestRequest.getComment(), locale);
        return ResponseEntity.noContent().build();
    }

    @Override
    @SoSuValidated
    public ResponseEntity<List<SuggestDTO>> getUserSuggests(String userName, Integer page, String jwtToken) {
        securityCheckUtil.checkUserValidity(userName, jwtToken);
        return ResponseEntity.ok(suggestService.findUserSuggests(userName, Objects.requireNonNullElse(page, 0)));
    }

    @Override
    @SoSuValidated
    public ResponseEntity<Void> likeSuggest(String userName, Long suggestId, String jwtToken) {
        securityCheckUtil.checkUserValidity(userName, jwtToken);
        suggestService.likeSuggest(userName, suggestId);
        return ResponseEntity.noContent().build();
    }
}
