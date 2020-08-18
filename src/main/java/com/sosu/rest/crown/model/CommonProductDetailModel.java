/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommonProductDetailModel {

    @Schema(description = "Product feature name", example = "author")
    private String value;

    @Schema(description = "Product feature", example = "Oguz Kahraman")
    private String feature;

}
