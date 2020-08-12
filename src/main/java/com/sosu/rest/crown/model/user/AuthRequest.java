/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.model.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AuthRequest {

    @Schema(description = "Username or mail", example = "exampleusername", required = true)
    private String username;

    @Schema(description = "Password of user", example = "123456", required = true)
    private String password;

}
