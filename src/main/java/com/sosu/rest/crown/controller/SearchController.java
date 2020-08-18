/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.controller;

import com.sosu.rest.crown.model.SearchResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Product Search", description = "Product searching API")
public interface SearchController {

    @Operation(summary = "Search product from name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the products", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = SearchResponseDTO[].class))}),
            @ApiResponse(responseCode = "404", description = "Products not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Request not valid", content = @Content)})
    @GetMapping(value = "/searchByName", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<SearchResponseDTO>> searchByName(@Parameter(description = "Name of the product", required = true, example = "Valentine") @RequestParam String name);

}
