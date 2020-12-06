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
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class CommonProductDTO {

    public CommonProductDTO(String name) {
        this.name = name;
    }

    @Schema(description = "Id of product", example = "1")
    private Long id;

    @Schema(description = "Type of product", example = "GAME")
    private ProductType productType;

    @Schema(description = "Name of product", example = "Valentine")
    private String name;

    @Schema(description = "Description of product", example = "Valentine")
    private String description;

    @Schema(description = "Image url of product", example = "http://example.png")
    private String image;

    @Schema(description = "Main categories of product")
    private Set<String> mainCategories;

    @Schema(description = "Main category names of product")
    private Set<String> mainCategoryNames;

    @Schema(description = "Console categories of product")
    private Set<String> consoleCategories;

    @Schema(description = "Console category names of product")
    private Set<String> consoleCategoryNames;

    @Schema(description = "Categories of product")
    private Set<String> categories;

    @Schema(description = "Category names of product")
    private Set<String> categoryNames;

}
