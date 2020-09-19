/**
 * @author : Oguz Kahraman
 * @since : 18.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.controller;

import com.sosu.rest.crown.model.request.ProductCommentRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Locale;

@Tag(name = "Comment", description = "Product comment API")
public interface ProductCommentController {

    @Operation(summary = "Get product from category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Added New Comment", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Request Error", content = @Content)})
    @PutMapping(value = "/addComment/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> getProductByCategory(@Parameter(description = "Request object", required = true) @RequestBody ProductCommentRequest request,
                                              @Parameter(description = "Product Id", required = true, example = "162691") @PathVariable Long productId,
                                              Locale locale);

}
