/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.controller;

import com.sosu.rest.crown.enums.ProductType;
import com.sosu.rest.crown.model.CommonProductDTO;
import com.sosu.rest.crown.model.CommonProductDetailDTO;
import com.sosu.rest.crown.model.request.ProductByCategorySearchRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Product", description = "Product getting API")
public interface ProductController {

    @Operation(summary = "Get product from category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the products", content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = CommonProductDTO.class)))}),
            @ApiResponse(responseCode = "400", description = "Request Error", content = @Content),
            @ApiResponse(responseCode = "404", description = "Products not found", content = @Content)})
    @GetMapping(value = "/getProductByCategory", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<CommonProductDTO>> getProductByCategory(@Parameter(description = "Request object")
                                                                @Valid ProductByCategorySearchRequest request);

    @Operation(summary = "Get random products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the products", content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = CommonProductDTO.class)))}),
            @ApiResponse(responseCode = "400", description = "Request Error", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)})
    @GetMapping(value = "/getRandomProducts", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<CommonProductDTO>> getRandomProducts(@Parameter(description = "Page number of products", example = "1")
                                                             @RequestParam(required = false) Integer page);

    @Operation(summary = "Get product detail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the product", content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = CommonProductDetailDTO.class)))}),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)})
    @GetMapping(value = "/getProductDetail/{productType}/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CommonProductDetailDTO> getProductDetail(@Parameter(description = "Id of product", example = "165") @PathVariable Long productId,
                                                            @Parameter(description = "Type of product", example = "PRODUCT") @PathVariable ProductType productType);

}
