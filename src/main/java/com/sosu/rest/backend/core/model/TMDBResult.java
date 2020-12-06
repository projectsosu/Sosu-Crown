/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.core.model;

import lombok.Data;

@Data
public class TMDBResult {

    private String poster_path;
    private Boolean adult;
    private String overview;
    private String release_date;
    private String first_air_date;
    private Integer[] genre_ids;
    private Integer id;
    private String name;
    private String title;


}
