/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.core.model;

import lombok.Data;

import java.util.List;

@Data
public class ImdbResult {

    private List<GeneralImdbResult> movie_results;
    private List<GeneralImdbResult> tv_results;

}
