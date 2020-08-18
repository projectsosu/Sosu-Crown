/**
 * @author : Oguz Kahraman
 * @since : 19.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class CommonProductDetailDTO extends CommonProductDTO {

    private List<CommonProductDetailModel> productFeatures;

    @Schema(description = "Product Publisher", example = "Oguz Kahraman")
    private String publisher;

    @Schema(description = "Product develper", example = "Oguz Kahraman")
    private String developer;
}
