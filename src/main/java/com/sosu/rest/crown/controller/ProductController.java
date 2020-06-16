package com.sosu.rest.crown.controller;

import com.sosu.rest.crown.entity.postgres.Product;
import com.sosu.rest.crown.model.ProductByCategorySearchRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Product", description = "Product getting API")
public interface ProductController {

    @Operation(summary = "Get product from name and year")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the products", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Product.class))}),
            @ApiResponse(responseCode = "404", description = "Products not found", content = @Content)})
    @GetMapping(value = "/getProduct", produces = MediaType.APPLICATION_JSON_VALUE)
    Product getProducts(@Parameter(description = "Name of the product", required = true, example = "Valentine") @RequestParam String name,
                        @Parameter(description = "Release date of the product", required = true, example = "2023") @RequestParam int year);

    @Operation(summary = "Get product from category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the products", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Product.class))}),
            @ApiResponse(responseCode = "404", description = "Products not found", content = @Content)})
    @PostMapping(value = "/getProductByCategory", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<Product> getProductByCategory(@RequestBody ProductByCategorySearchRequest request);

}
