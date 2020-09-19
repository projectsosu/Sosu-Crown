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
import com.sosu.rest.crown.mapper.SuggestMapper;
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
        Suggest suggest = suggestRepository.findById(suggestId).orElse(null);
        if (suggest == null) {
            throw new SoSuException(HttpStatus.BAD_REQUEST, "Suggest id is incorrect", "SUGGEST_NOT_FOUND");
        }
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

}
