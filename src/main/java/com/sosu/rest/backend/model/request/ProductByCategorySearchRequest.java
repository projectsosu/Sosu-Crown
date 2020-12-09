/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.model.request;

import com.sosu.rest.backend.enums.ProductField;
import com.sosu.rest.backend.enums.ProductType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ProductByCategorySearchRequest {

    @NotBlank
    @Schema(description = "Id of category", example = "5ee73fdb2ace0520f01af3dc", required = true)
    private String categoryId;

    @NotNull
    @Schema(description = "Type of search category", example = "PRODUCT", required = true)
    private ProductType productType;

    @Schema(description = "Return limit", example = "1")
    private Integer page = 1;

    @Schema(description = "Return limit", example = "10")
    private Integer pageSize = 10;

    @Schema(description = "Sort field", example = "NAME", implementation = ProductField.class)
    private ProductField sortBy = ProductField.NAME;

    @Schema(description = "Sort type", example = "true")
    private Boolean desc = false;

}