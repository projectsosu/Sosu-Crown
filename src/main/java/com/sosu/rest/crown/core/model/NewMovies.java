package com.sosu.rest.crown.core.model;

import lombok.Data;

import java.util.List;

@Data
public class NewMovies {

     private Integer page;
     private Integer total_results;
     private List<TMDBResult> results;
}
