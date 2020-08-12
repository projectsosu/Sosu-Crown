/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.model;

import com.sosu.rest.crown.enums.ProductType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SearchResponseModel {

    @Schema(description = "Name of product", example = "Valentine")
    private String name;

    @Schema(description = "Id of product", example = "1")
    private Long id;

    @Schema(description = "Type of product", example = "GAME")
    private ProductType productType;
}
