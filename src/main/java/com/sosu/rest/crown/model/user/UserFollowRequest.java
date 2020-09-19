/**
 * @author : Oguz Kahraman
 * @since : 19.09.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.model.user;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Tag(name = "User", description = "Follow request model")
public class UserFollowRequest {

    @Schema(description = "Follower user name", example = "exampleusername")
    @NotBlank
    private String follower;

    @Schema(description = "Followed user name", example = "exampleusername2")
    @NotBlank
    private String followed;

}
