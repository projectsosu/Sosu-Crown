/**
 * @author : Oguz Kahraman
 * @since : 19.09.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class NewSuggestRequest {

    @NotBlank
    @Schema(description = "User comment", example = "Watch it", required = true)
    private String comment;

    @NotBlank
    @Schema(description = "Username of user", example = "example", required = true)
    private String userName;

}
