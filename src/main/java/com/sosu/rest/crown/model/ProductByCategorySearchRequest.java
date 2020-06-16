package com.sosu.rest.crown.model;

import com.sosu.rest.crown.enums.ProductField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ProductByCategorySearchRequest {

    @NotBlank
    @Schema(description = "Id of category", example = "5ee73fdb2ace0520f01af3dc", required = true)
    private String category_id;

    @NotBlank
    @Schema(description = "Return limit", example = "10", required = true)
    private int pageSize;

    @NotBlank
    @Schema(description = "Sort field", example = "NAME", required = true, implementation = ProductField.class)
    private ProductField sortBy;

    @NotBlank
    @Schema(description = "Sort type", example = "true", required = true)
    private boolean desc;

}
