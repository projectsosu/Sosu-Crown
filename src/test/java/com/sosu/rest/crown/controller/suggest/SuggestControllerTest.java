/**
 * @author : Oguz Kahraman
 * @since : 19.09.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.controller.suggest;

import com.sosu.rest.crown.controller.suggest.impl.SuggestControllerImpl;
import com.sosu.rest.crown.core.util.SecurityCheckUtil;
import com.sosu.rest.crown.model.request.NewSuggestRequest;
import com.sosu.rest.crown.model.suggest.SuggestDTO;
import com.sosu.rest.crown.service.suggest.SuggestService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SuggestControllerTest {

    @Mock
    private SuggestService suggestService;

    @Mock
    private SecurityCheckUtil securityCheckUtil;

    @InjectMocks
    private SuggestControllerImpl suggestController;

    @Test
    void addNewSuggest() {
        ResponseEntity<Void> responseEntity = suggestController.addNewSuggest(mock(NewSuggestRequest.class), "123", "123", Locale.ENGLISH);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(suggestService, times(1)).addNewSuggest(any(), any(), any());
    }

    @Test
    void getUserSuggests() {
        LocalDateTime dateTime = LocalDateTime.now();
        SuggestDTO suggestDTO = new SuggestDTO();
        suggestDTO.setLang("en");
        suggestDTO.setUserName("example");
        suggestDTO.setSuggest("exampleSuggest");
        suggestDTO.setDate(dateTime);
        when(suggestService.findUserSuggests(any(), any())).thenReturn(Collections.singletonList(suggestDTO));
        ResponseEntity<List<SuggestDTO>> responseEntity = suggestController.getUserSuggests("example", 0, "123");
        SuggestDTO response = Objects.requireNonNull(responseEntity.getBody()).get(0);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(suggestDTO.getSuggest(), response.getSuggest());
        assertEquals(suggestDTO.getLang(), response.getLang());
        assertEquals(suggestDTO.getUserName(), response.getUserName());
        assertEquals(suggestDTO.getDate(), response.getDate());
    }

    @Test
    void likeSuggest() {
        ResponseEntity<Void> responseEntity = suggestController.likeSuggest("123", 123L, "123");
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(suggestService, times(1)).likeSuggest(any(), any());
    }
}