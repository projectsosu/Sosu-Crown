/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.core.model;

import lombok.Data;

import java.util.List;

@Data
public class ProductDetail {

    private List<Genre> genres;
    private String imdb_id = "";
    private String release_date;
    private String first_air_date;

}
