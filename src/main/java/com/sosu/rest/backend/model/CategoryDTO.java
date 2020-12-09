/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.model;

import com.sosu.rest.backend.enums.ProductType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CategoryDTO {

    @Schema(description = "Id of category", example = "5ee73ec6973ea9022e68a497")
    private String categoryId;

    @Schema(description = "Lang of category", example = "en")
    private String lang;

    @Schema(description = "Name of category", example = "Example")
    private String name;

    @Schema(description = "Activity status of category", example = "true")
    private Boolean active;

    @Schema(description = "Type of category", example = "GAME")
    private ProductType type;

    @Schema(description = "Parent id of category", example = "Parent id")
    private String parentId;

    @Schema(description = "Default id of category", example = "Default id")
    private String defaultCategory;

    @Schema(description = "Is parent console", example = "Example")
    private Boolean console;

    @Schema(description = "Item count", example = "123")
    private Integer itemCount;
}