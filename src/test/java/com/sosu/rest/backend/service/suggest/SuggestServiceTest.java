/**
 * @author : Oguz Kahraman
 * @since : 19.09.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.service.suggest;

import com.sosu.rest.backend.core.exception.SoSuException;
import com.sosu.rest.backend.entity.postgres.LikedSuggest;
import com.sosu.rest.backend.mapper.SuggestMapper;
import com.sosu.rest.backend.model.suggest.SuggestCommentDTO;
import com.sosu.rest.backend.model.suggest.SuggestCommentRequest;
import com.sosu.rest.backend.model.suggest.SuggestDTO;
import com.sosu.rest.backend.repo.postgres.LikedSuggestRepository;
import com.sosu.rest.backend.repo.postgres.SuggestCommentRepository;
import com.sosu.rest.backend.repo.postgres.SuggestRepository;
import com.sosu.rest.backend.service.suggest.impl.SuggestServiceImpl;
import com.sosu.rest.backend.service.user.UserService;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

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

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


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
        when(suggestRepository.existsById(any())).thenReturn(true);
        suggestService.likeSuggest("123", 12L);
        verify(likedSuggestRepository, times(1)).save(any());
    }

    @Test
    void likeSuggestDelete() {
        when(suggestRepository.existsById(any())).thenReturn(true);
        when(likedSuggestRepository.findBySuggestIdAndUserName(any(), any())).thenReturn(new LikedSuggest());
        suggestService.likeSuggest("123", 12L);
        verify(likedSuggestRepository, times(1)).delete(any());
    }

    @Test
    void getSuggestComments() {
        SuggestCommentDTO suggestCommentDTO = getSuggestCommentDTO();
        when(suggestRepository.existsById(any())).thenReturn(true);
        when(suggestCommentRepository.findBySuggestIdOrderByDateDesc(any(), any())).thenReturn(new ArrayList<>());
        when(suggestMapper.commentEntityListToDtoList(any())).thenReturn(Collections.singletonList(suggestCommentDTO));
        List<SuggestCommentDTO> suggestCommentDTOS = suggestService.getSuggestComments(1L, 0);
        SuggestCommentDTO response = Objects.requireNonNull(suggestCommentDTOS).get(0);
        checkAssertion(suggestCommentDTO, response);
    }

    @Test
    void addNewCommentToCommentParent() {
        SuggestCommentRequest request = new SuggestCommentRequest();
        request.setParentId(1L);
        when(suggestCommentRepository.existsById(any())).thenReturn(true);
        suggestService.addNewCommentToComment(request);
        verify(suggestCommentRepository, times(1)).save(any());
    }

    @Test
    void addNewCommentToComment() {
        SuggestCommentRequest request = new SuggestCommentRequest();
        request.setSuggestId(1L);
        when(suggestRepository.existsById(any())).thenReturn(true);
        suggestService.addNewCommentToComment(new SuggestCommentRequest());
        verify(suggestCommentRepository, times(1)).save(any());
    }


    @Test
    void getCommentsOfComments() {
        SuggestCommentDTO suggestCommentDTO = getSuggestCommentDTO();
        when(suggestCommentRepository.existsById(any())).thenReturn(true);
        when(suggestCommentRepository.findByParentIdOrderByDateDesc(any(), any())).thenReturn(new ArrayList<>());
        when(suggestMapper.commentEntityListToDtoList(any())).thenReturn(Collections.singletonList(suggestCommentDTO));
        List<SuggestCommentDTO> suggestCommentDTOS = suggestService.getCommentsOfComments(1L, 0);
        SuggestCommentDTO response = Objects.requireNonNull(suggestCommentDTOS).get(0);
        checkAssertion(suggestCommentDTO, response);
    }

    @Test
    void getCommentsOfCommentsException() {
        SoSuException exception = assertThrows(SoSuException.class, () -> suggestService.getCommentsOfComments(12L, 1));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Suggest comment id is incorrect", exception.getReason());
        assertEquals("SUGGEST_COMMENT_NOT_FOUND", exception.getCause().getMessage());
    }

    @NotNull
    private SuggestCommentDTO getSuggestCommentDTO() {
        SuggestCommentDTO suggestCommentDTO = new SuggestCommentDTO();
        suggestCommentDTO.setId(0L);
        suggestCommentDTO.setComment("123");
        suggestCommentDTO.setUserName("exampleun");
        suggestCommentDTO.setDate(LocalDateTime.now());
        suggestCommentDTO.setLikeCount(1L);
        suggestCommentDTO.setCommentCount(2L);
        suggestCommentDTO.setReSuggestCount(3L);
        return suggestCommentDTO;
    }


    private void checkAssertion(SuggestCommentDTO suggestCommentDTO, SuggestCommentDTO response) {
        assertEquals(suggestCommentDTO.getUserName(), response.getUserName());
        assertEquals(suggestCommentDTO.getDate(), response.getDate());
        assertEquals(suggestCommentDTO.getId(), response.getId());
        assertEquals(suggestCommentDTO.getComment(), response.getComment());
        assertEquals(suggestCommentDTO.getLikeCount(), response.getLikeCount());
        assertEquals(suggestCommentDTO.getReSuggestCount(), response.getReSuggestCount());
        assertEquals(suggestCommentDTO.getCommentCount(), response.getCommentCount());
    }

}