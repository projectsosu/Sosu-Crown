package com.sosu.rest.crown.controller;

import com.sosu.rest.crown.model.user.AuthRequest;
import com.sosu.rest.crown.model.user.UserModel;
import com.sosu.rest.crown.model.user.UserRegisterRequest;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Tag(name = "User Operations", description = "User auth operations")
public interface UserController {

    @Operation(summary = "Login user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login success", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserModel.class))}),
            @ApiResponse(responseCode = "401", description = "Bad credential", content = @Content),
            @ApiResponse(responseCode = "400", description = "Request not valid", content = @Content)})
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity creteToken(@RequestBody @Valid AuthRequest authRequest);

    @Operation(summary = "Register user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Register success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Request not valid", content = @Content)})
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity register(@RequestBody @Valid UserRegisterRequest registerRequest);

    @Operation(summary = "Validate user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Validate success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Request not valid", content = @Content)})
    @GetMapping(value = "/validate/{username}/{token}")
    ResponseEntity validate(@Parameter(description = "Username", required = true, example = "example") @PathVariable String username,
                            @Parameter(description = "Toke for validation", required = true, example = "example") @PathVariable String token);
}