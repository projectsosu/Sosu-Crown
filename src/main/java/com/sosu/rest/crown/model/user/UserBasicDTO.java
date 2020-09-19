/**
 * @author : Oguz Kahraman
 * @since : 19.09.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.model.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserBasicDTO {

    @Schema(description = "Name of user", example = "Sosu", required = true)
    @NotEmpty
    private String name;

    @Schema(description = "Username of user", example = "exampleusername", required = true)
    @NotEmpty
    private String username;

    @Schema(description = "Profile image url of user", example = "deneme.jpg", required = true)
    @NotEmpty
    private String image;

}
