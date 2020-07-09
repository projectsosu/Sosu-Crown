package com.sosu.rest.crown.model;

import com.sosu.rest.crown.enums.ProductType;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Tag(name = "Common Product", description = "Search response for products")
public class CommonProductModel {

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

    @Schema(description = "Categories of product")
    private List<String> categories;

}
