/**
 * @author : Oguz Kahraman
 * @since : 19.09.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SuggestDTO {

    private String suggest;
    private String lang;
    private String userName;
    private LocalDateTime date;

}
