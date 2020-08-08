package com.sosu.rest.crown.model;

import com.sosu.rest.crown.enums.ProductType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CategoryDTO {

    @Schema(description = "Lang of category", example = "en_US")
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
