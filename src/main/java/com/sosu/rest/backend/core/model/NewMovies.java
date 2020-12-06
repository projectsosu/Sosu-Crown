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
public class NewMovies {

     private Integer page;
     private Integer total_results;
     private List<TMDBResult> results;
}
