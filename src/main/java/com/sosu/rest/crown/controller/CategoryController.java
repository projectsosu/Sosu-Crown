package com.sosu.rest.crown.controller;

import com.sosu.rest.crown.model.CategoryDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Category", description = "Category getting API")
public interface CategoryController {

    @Operation(summary = "Get categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category list", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = CategoryDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Request Error", content = @Content),
            @ApiResponse(responseCode = "404", description = "Category getting error", content = @Content)})
    @GetMapping(value = "/getProductByCategory", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity getCategoryList(@RequestParam(required = false) String lang);
}
