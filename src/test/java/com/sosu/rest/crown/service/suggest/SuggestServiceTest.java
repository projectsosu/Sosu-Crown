/**
 * @author : Oguz Kahraman
 * @since : 19.09.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.service.suggest;

import com.sosu.rest.crown.mapper.SuggestMapper;
import com.sosu.rest.crown.model.SuggestDTO;
import com.sosu.rest.crown.repo.postgres.SuggestRepository;
import com.sosu.rest.crown.service.suggest.impl.SuggestServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SuggestServiceTest {

    @Mock
    private SuggestRepository suggestRepository;

    @Mock
    private SuggestMapper suggestMapper;

    @InjectMocks
    private SuggestServiceImpl suggestService;

    @Test
    void addNewSuggest() {
        suggestService.addNewSuggest("anyString()", "anyString()", Locale.ENGLISH);
        verify(suggestRepository, times(1)).save(any());
    }

    @Test
    void findUserSuggests() {
        LocalDateTime dateTime = LocalDateTime.now();
        SuggestDTO suggestDTO = new SuggestDTO();
        suggestDTO.setLang("en");
        suggestDTO.setUserName("example");
        suggestDTO.setSuggest("exampleSuggest");
        suggestDTO.setDate(dateTime);
        when(suggestRepository.findByUserName(any(), any())).thenReturn(new ArrayList<>());
        when(suggestMapper.entityListToDtoList(any())).thenReturn(Collections.singletonList(suggestDTO));
        List<SuggestDTO> suggestDTOS = suggestService.findUserSuggests("user", 0);
        SuggestDTO response = Objects.requireNonNull(suggestDTOS).get(0);
        assertEquals(suggestDTO.getUserName(), response.getUserName());
        assertEquals(suggestDTO.getDate(), response.getDate());
        assertEquals(suggestDTO.getLang(), response.getLang());
        assertEquals(suggestDTO.getSuggest(), response.getSuggest());
    }
}