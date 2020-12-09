/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.controller;

import com.sosu.rest.backend.model.user.UserBasicDTO;
import com.sosu.rest.backend.model.user.UserFollowRequest;
import com.sosu.rest.backend.core.annotations.SoSuValidated;
import com.sosu.rest.backend.model.user.AuthRequest;
import com.sosu.rest.backend.model.user.UserModel;
import com.sosu.rest.backend.model.user.UserRegisterRequest;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@Tag(name = "User Operations", description = "User auth operations")
public interface UserController {

    @Operation(summary = "Login user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login success", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserModel.class))}),
            @ApiResponse(responseCode = "401", description = "Bad credential", content = @Content),
            @ApiResponse(responseCode = "400", description = "Request not valid", content = @Content)})
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserModel> login(@RequestBody @Valid AuthRequest authRequest);

    @Operation(summary = "Register user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Register success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Request not valid", content = @Content)})
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> register(@RequestBody @Valid UserRegisterRequest registerRequest, @Parameter(hidden = true) Locale locale);

    @Operation(summary = "Validate user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Validate success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Request not valid", content = @Content)})
    @GetMapping(value = "/validate/{username}/{token}")
    ResponseEntity<Void> validate(@Parameter(description = "Username", required = true, example = "example") @PathVariable String username,
                                  @Parameter(description = "Toke for validation", required = true, example = "example") @PathVariable String token);

    @Operation(summary = "Upload profile image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Validate success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Request not valid", content = @Content)})
    @PostMapping(value = "/uploadImage/{username}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<Void> uploadFile(@Parameter(description = "Image information", required = true) @RequestPart MultipartFile file,
                                    @Parameter(description = "Username", required = true, example = "example") @PathVariable String username,
                                    @RequestPart UserFollowRequest request);

    @Operation(summary = "Returns user basic information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get user basic success", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserBasicDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized user", content = @Content),
            @ApiResponse(responseCode = "400", description = "Request not valid", content = @Content)})
    @GetMapping(value = "/getUserBasic/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserBasicDTO> getUserBasic(@Parameter(description = "Username", required = true, example = "example") @PathVariable String username);

    @Operation(summary = "Returns following users basic information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get following user basic success", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserBasicDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized user", content = @Content),
            @ApiResponse(responseCode = "400", description = "Request not valid", content = @Content)})
    @GetMapping(value = "/getFollowedUsers/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<UserBasicDTO>> getFollowedUsers(@Parameter(description = "Username", required = true, example = "example") @PathVariable String username);

    @Operation(summary = "Returns follower users basic information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get follower user basic success", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserBasicDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized user", content = @Content),
            @ApiResponse(responseCode = "400", description = "Request not valid", content = @Content)})
    @GetMapping(value = "/getFollowerUsers/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<UserBasicDTO>> getFollowerUsers(@Parameter(description = "Username", required = true, example = "example") @PathVariable String username);

    @SoSuValidated
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User follow or unfollow success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "400", description = "Request not valid", content = @Content)})
    @PatchMapping(value = "/followUser", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> followUser(@RequestBody UserFollowRequest request,
                                    @RequestHeader("Authorization") String jwtToken);
}