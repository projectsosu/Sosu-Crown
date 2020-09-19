/**
 * @author : Oguz Kahraman
 * @since : 18.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.model.request;

import com.sosu.rest.crown.enums.ProductType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ProductCommentRequest {

    @NotNull
    @Schema(description = "Type of product", example = "PRODUCT", required = true)
    private ProductType type;

    @NotBlank
    @Schema(description = "Comment", example = "Comment", required = true)
    private String comment;

    @Schema(description = "Name of user", example = "5f3684de2809ee27dc272307")
    private String userName;

}
