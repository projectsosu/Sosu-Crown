package com.sosu.rest.crown.core.model;

import lombok.Data;

import java.util.List;

@Data
public class ImdbResult {

    private List<GeneralImdbResult> movie_results;
    private List<GeneralImdbResult> tv_results;

}
