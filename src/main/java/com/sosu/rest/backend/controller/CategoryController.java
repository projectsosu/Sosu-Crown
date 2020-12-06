/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.controller;

import com.sosu.rest.backend.model.CategoryDTO;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Locale;

@Tag(name = "Category", description = "Category getting API")
public interface CategoryController {

    @Operation(summary = "Get categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category list", content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = CategoryDTO.class)))}),
            @ApiResponse(responseCode = "400", description = "Request Error", content = @Content),
            @ApiResponse(responseCode = "404", description = "Category getting error", content = @Content)})
    @GetMapping(value = "/getCategoryList", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<CategoryDTO>> getCategoryList(@Parameter(hidden = true) Locale locale);

    @Operation(summary = "Get categories by parent id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category list", content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = CategoryDTO.class)))}),
            @ApiResponse(responseCode = "400", description = "Request Error", content = @Content),
            @ApiResponse(responseCode = "404", description = "Category getting  by parent id error", content = @Content)})
    @GetMapping(value = "/findByParentId", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<CategoryDTO>> findByParentId(@Parameter(description = "Parent category id", example = "5edd816e7d85f9caded1c5bb")
                                                     @RequestParam String categoryId);
}
