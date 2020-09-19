/**
 * @author : Oguz Kahraman
 * @since : 19.09.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.service.suggest.impl;

import com.sosu.rest.crown.entity.postgres.Suggest;
import com.sosu.rest.crown.mapper.SuggestMapper;
import com.sosu.rest.crown.model.SuggestDTO;
import com.sosu.rest.crown.repo.postgres.SuggestRepository;
import com.sosu.rest.crown.service.suggest.SuggestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Service
public class SuggestServiceImpl implements SuggestService {

    @Autowired
    private SuggestRepository suggestRepository;

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
        return suggestMapper.entityListToDtoList(suggestRepository.findByUserName(userName, PageRequest.of(page, 10, Sort.by("date").descending())));
    }

}
