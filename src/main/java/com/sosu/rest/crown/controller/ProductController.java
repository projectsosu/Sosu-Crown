package com.sosu.rest.crown.controller;

import com.sosu.rest.crown.entity.postgres.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ProductController {

    @Operation(summary = "Get product from name and year")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the products", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Product.class))}),
            @ApiResponse(responseCode = "404", description = "Products not found", content = @Content)})
    @PostMapping("/getProduct")
    Product getProducts(@RequestParam String name, @RequestParam int year);

    @Operation(summary = "Get product from category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the products", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Product.class))}),
            @ApiResponse(responseCode = "404", description = "Products not found", content = @Content)})
    @PostMapping("/getProductByCategory")
    List<Product> getProductByCategory(@RequestParam String category_id, @RequestParam int pageSize, @RequestParam String sortBy, @RequestParam boolean desc);

}
