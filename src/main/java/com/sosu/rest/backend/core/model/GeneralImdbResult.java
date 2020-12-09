/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.core.model;

import lombok.Data;

@Data
public class GeneralImdbResult {

    private Integer[] genre_ids;
    private Integer id;

}