/**
 * @author : Oguz Kahraman
 * @since : 19.09.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.service.suggest;

import com.sosu.rest.crown.model.suggest.SuggestCommentDTO;
import com.sosu.rest.crown.model.suggest.SuggestCommentRequest;
import com.sosu.rest.crown.model.suggest.SuggestDTO;

import java.util.List;
import java.util.Locale;

public interface SuggestService {

    void addNewSuggest(String userName, String suggest, Locale locale);

    List<SuggestDTO> findUserSuggests(String userName, Integer page);

    void likeSuggest(String userName, Long suggestId);

    List<SuggestCommentDTO> getSuggestComments(Long suggestId, Integer page);

    void addNewCommentToComment(SuggestCommentRequest request);

    List<SuggestCommentDTO> getCommentsOfComments(Long parentId, Integer page);
}
