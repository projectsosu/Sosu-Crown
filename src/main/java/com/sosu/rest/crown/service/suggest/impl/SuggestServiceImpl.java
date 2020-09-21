/**
 * @author : Oguz Kahraman
 * @since : 19.09.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.service.suggest.impl;

import com.sosu.rest.crown.core.exception.SoSuException;
import com.sosu.rest.crown.entity.postgres.LikedSuggest;
import com.sosu.rest.crown.entity.postgres.Suggest;
import com.sosu.rest.crown.entity.postgres.SuggestComment;
import com.sosu.rest.crown.mapper.SuggestMapper;
import com.sosu.rest.crown.model.suggest.SuggestCommentDTO;
import com.sosu.rest.crown.model.suggest.SuggestCommentRequest;
import com.sosu.rest.crown.model.suggest.SuggestDTO;
import com.sosu.rest.crown.repo.postgres.LikedSuggestRepository;
import com.sosu.rest.crown.repo.postgres.SuggestCommentRepository;
import com.sosu.rest.crown.repo.postgres.SuggestRepository;
import com.sosu.rest.crown.service.suggest.SuggestService;
import com.sosu.rest.crown.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Service
public class SuggestServiceImpl implements SuggestService {

    @Autowired
    private SuggestRepository suggestRepository;

    @Autowired
    private LikedSuggestRepository likedSuggestRepository;

    @Autowired
    private SuggestCommentRepository suggestCommentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SuggestMapper suggestMapper;

    /**
     * This methods add new suggest for user with date
     *
     * @param userName name of user
     * @param suggest  user comment
     * @param locale   user locale
     */
    @Override
    public void addNewSuggest(String userName, String suggest, Locale locale) {
        Suggest suggestEnt = new Suggest();
        suggestEnt.setDate(LocalDateTime.now());
        suggestEnt.setLang(locale.getLanguage());
        suggestEnt.setUserSuggest(suggest);
        suggestEnt.setUserName(userName);
        suggestRepository.save(suggestEnt);
    }

    /**
     * This methods returns user suggests with descending sort by date
     *
     * @param userName name of user for search
     * @param page     searched page
     * @return user suggests
     */
    @Override
    public List<SuggestDTO> findUserSuggests(String userName, Integer page) {
        List<SuggestDTO> suggestDTOS = suggestMapper.entityListToDtoList(suggestRepository.findByUserName(userName, PageRequest.of(page, 10, Sort.by("date").descending())));
        suggestDTOS.parallelStream().forEach(item -> {
            item.setLikeCount(likedSuggestRepository.countBySuggestId(item.getId()));
            item.setCommentCount(suggestCommentRepository.countBySuggestId(item.getId()));
        });
        return suggestDTOS;
    }

    /**
     * This methods add new suggest for user with date
     *
     * @param userName  name of user
     * @param suggestId id of comment
     */
    @Override
    public void likeSuggest(String userName, Long suggestId) {
        checkSuggest(suggestId);
        userService.checkUserValidity(userName);
        LikedSuggest likedSuggest = likedSuggestRepository.findBySuggestIdAndUserName(suggestId, userName);
        if (likedSuggest != null) {
            likedSuggestRepository.delete(likedSuggest);
        } else {
            likedSuggest = new LikedSuggest();
            likedSuggest.setSuggestId(suggestId);
            likedSuggest.setUserName(userName);
            likedSuggestRepository.save(likedSuggest);
        }
    }

    /**
     * This gets suggest comments for user
     *
     * @param suggestId id of comment
     * @param page      page of comment
     * @return returns suggest comments
     */
    @Override
    public List<SuggestCommentDTO> getSuggestComments(Long suggestId, Integer page) {
        checkSuggest(suggestId);
        List<SuggestComment> suggestComments = suggestCommentRepository.findBySuggestIdOrderByDateDesc(suggestId, PageRequest.of(page, 10));
        List<SuggestCommentDTO> suggestCommentDTOS = suggestMapper.commentEntityListToDtoList(suggestComments);
        suggestCommentDTOS.parallelStream().forEach(item -> {
            item.setCommentCount(suggestCommentRepository.countByParentId(item.getId()));
        });
        return suggestCommentDTOS;
    }

    /**
     * This adds new suggest comments for user
     *
     * @param request new suggest comment
     */
    @Override
    public void addNewCommentToComment(SuggestCommentRequest request) {
        SuggestComment suggestComment = new SuggestComment();
        if (request.getParentId() != null) {
            checkParent(request.getParentId());
            suggestComment.setParentId(request.getParentId());
        } else {
            checkSuggest(request.getSuggestId());
        }
        suggestComment.setComment(request.getComment());
        suggestComment.setUserName(request.getComment());
        suggestCommentRepository.save(suggestComment);
    }

    /**
     * This gets comments for comments
     *
     * @param parentId id of comment
     * @param page     page of comment
     * @return returns suggest comments
     */
    @Override
    public List<SuggestCommentDTO> getCommentsOfComments(Long parentId, Integer page) {
        checkParent(parentId);
        List<SuggestComment> suggestComments = suggestCommentRepository.findByParentIdOrderByDateDesc(parentId, PageRequest.of(page, 10));
        List<SuggestCommentDTO> suggestCommentDTOS = suggestMapper.commentEntityListToDtoList(suggestComments);
        suggestCommentDTOS.parallelStream().forEach(item -> {
            item.setCommentCount(suggestCommentRepository.countByParentId(item.getId()));
        });
        return suggestCommentDTOS;
    }

    public void checkSuggest(Long suggestId) {
        Boolean suggest = suggestRepository.existsById(suggestId);
        if (!suggest) {
            throw new SoSuException(HttpStatus.BAD_REQUEST, "Suggest id is incorrect", "SUGGEST_NOT_FOUND");
        }
    }

    public void checkParent(Long parentId) {
        Boolean suggest = suggestCommentRepository.existsById(parentId);
        if (!suggest) {
            throw new SoSuException(HttpStatus.BAD_REQUEST, "Suggest comment id is incorrect", "SUGGEST_COMMENT_NOT_FOUND");
        }
    }

}
