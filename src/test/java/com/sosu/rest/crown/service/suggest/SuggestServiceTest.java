/**
 * @author : Oguz Kahraman
 * @since : 19.09.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.service.suggest;

import com.sosu.rest.crown.core.exception.SoSuException;
import com.sosu.rest.crown.entity.postgres.LikedSuggest;
import com.sosu.rest.crown.entity.postgres.Suggest;
import com.sosu.rest.crown.mapper.SuggestMapper;
import com.sosu.rest.crown.model.suggest.SuggestDTO;
import com.sosu.rest.crown.repo.postgres.LikedSuggestRepository;
import com.sosu.rest.crown.repo.postgres.SuggestCommentRepository;
import com.sosu.rest.crown.repo.postgres.SuggestRepository;
import com.sosu.rest.crown.service.suggest.impl.SuggestServiceImpl;
import com.sosu.rest.crown.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SuggestServiceTest {

    @Mock
    private SuggestRepository suggestRepository;

    @Mock
    private LikedSuggestRepository likedSuggestRepository;

    @Mock
    private SuggestCommentRepository suggestCommentRepository;

    @Mock
    private UserService userService;

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

    @Test
    void likeSuggestException() {
        SoSuException exception = assertThrows(SoSuException.class, () -> suggestService.likeSuggest("ASD", 12L));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Suggest id is incorrect", exception.getReason());
        assertEquals("SUGGEST_NOT_FOUND", exception.getCause().getMessage());
    }

    @Test
    void likeSuggestSave() {
        when(suggestRepository.findById(any())).thenReturn(Optional.of(new Suggest()));
        suggestService.likeSuggest("123", 12L);
        verify(likedSuggestRepository, times(1)).save(any());
    }

    @Test
    void likeSuggestDelete() {
        when(suggestRepository.findById(any())).thenReturn(Optional.of(new Suggest()));
        when(likedSuggestRepository.findBySuggestIdAndUserName(any(), any())).thenReturn(new LikedSuggest());
        suggestService.likeSuggest("123", 12L);
        verify(likedSuggestRepository, times(1)).delete(any());
    }

}