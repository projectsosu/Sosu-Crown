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
import com.sosu.rest.crown.model.suggest.SuggestCommentDTO;
import com.sosu.rest.crown.model.suggest.SuggestCommentRequest;
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

    /**
     * This method returns of user suggests
     *
     * @param userName of user
     * @param page     shown
     * @param jwtToken token for security
     * @return suggest list
     */
    @Override
    @SoSuValidated
    public ResponseEntity<List<SuggestDTO>> getUserSuggests(String userName, Integer page, String jwtToken) {
        securityCheckUtil.checkUserValidity(userName, jwtToken);
        return ResponseEntity.ok(suggestService.findUserSuggests(userName, Objects.requireNonNullElse(page, 0)));
    }

    /**
     * @param userName  of user
     * @param suggestId of suggest
     * @param jwtToken  token for security
     */
    @Override
    @SoSuValidated
    public ResponseEntity<Void> likeSuggest(String userName, Long suggestId, String jwtToken) {
        securityCheckUtil.checkUserValidity(userName, jwtToken);
        suggestService.likeSuggest(userName, suggestId);
        return ResponseEntity.noContent().build();
    }


    /**
     * This methods adds new suggest comments or reply
     *
     * @param request  new reply
     * @param jwtToken token for security
     */
    @Override
    @SoSuValidated
    public ResponseEntity<Void> addSuggestReply(SuggestCommentRequest request, String jwtToken) {
        securityCheckUtil.checkUserValidity(request.getUserName(), jwtToken);
        suggestService.addNewCommentToComment(request);
        return ResponseEntity.noContent().build();
    }

    /**
     * This method returns suggest comments
     *
     * @param suggestId id of suggest
     * @param page      of page
     * @return suggest comments
     */
    @Override
    @SoSuValidated
    public ResponseEntity<List<SuggestCommentDTO>> getSuggestComments(Long suggestId, Integer page) {
        return ResponseEntity.ok(suggestService.getSuggestComments(suggestId, Objects.requireNonNullElse(page, 0)));
    }

    /**
     * This method returns suggest comments
     *
     * @param parentId id of comment
     * @param page     of page
     * @return comment replies
     */
    @Override
    @SoSuValidated
    public ResponseEntity<List<SuggestCommentDTO>> getCommentReplies(Long parentId, Integer page) {
        return ResponseEntity.ok(suggestService.getCommentsOfComments(parentId, Objects.requireNonNullElse(page, 0)));
    }
}
