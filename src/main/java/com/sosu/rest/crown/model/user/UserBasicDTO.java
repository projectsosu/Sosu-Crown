/**
 * @author : Oguz Kahraman
 * @since : 19.09.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.model.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserBasicDTO {

    @Schema(description = "Name of user", example = "Sosu", required = true)
    private String name;

    @Schema(description = "Username of user", example = "exampleusername", required = true)
    private String username;

    @Schema(description = "Profile image url of user", example = "deneme.jpg", required = true)
    private String image;

    @Schema(description = "Count of user followers", example = "1", required = true)
    private Long followerCount;

    @Schema(description = "Count of user following", example = "1", required = true)
    private Long followedCount;

}
