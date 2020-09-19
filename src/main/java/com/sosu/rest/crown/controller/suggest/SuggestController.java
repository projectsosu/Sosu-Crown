/**
 * @author : Oguz Kahraman
 * @since : 19.09.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.controller.suggest;

import com.sosu.rest.crown.core.annotations.SoSuValidated;
import com.sosu.rest.crown.model.SuggestDTO;
import com.sosu.rest.crown.model.request.NewSuggestRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@Tag(name = "Suggest", description = "Suggest API")
public interface SuggestController {

    @Operation(summary = "Add new suggest for user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Added New Suggest", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = "Unauthorized user", content = @Content),
            @ApiResponse(responseCode = "400", description = "Request Error", content = @Content)})
    @PutMapping(value = "/addSuggest", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> addNewSuggest(@RequestBody @Valid NewSuggestRequest newSuggestRequest, Locale locale);

    @SoSuValidated
    @Operation(summary = "Returns user suggests")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the suggests", content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = SuggestDTO.class)))}),
            @ApiResponse(responseCode = "404", description = "Suggests not found", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized user", content = @Content),
            @ApiResponse(responseCode = "400", description = "Request not valid", content = @Content)})
    @GetMapping(value = "/getUserSuggests/{userName}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<SuggestDTO>> getUserSuggests(@Parameter(description = "Name of user", required = true, example = "example") @PathVariable String userName,
                                                     @Parameter(description = "Response page", required = true, example = "0") @RequestParam Integer page);
}
