/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.model.user;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Tag(name = "User", description = "User model")
public class UserModel {

    @Schema(description = "Birth date of user", example = "2000-10-10", required = true)
    @NotNull
    private LocalDate birthDate;

    @Schema(description = "Mail address of user", example = "exampleusername@mail.com", required = true)
    @NotEmpty
    private String email;

    @Schema(description = "Name of user", example = "Sosu", required = true)
    @NotEmpty
    private String name;

    @Schema(description = "Username of user", example = "exampleusername", required = true)
    @NotEmpty
    private String username;

    @Schema(description = "Profile image url of user", example = "deneme.jpg", required = true)
    @NotEmpty
    private String image;

    @Schema(description = "Jwt token for session", example = "asdasd12321asdasdasd", required = true)
    @NotEmpty
    private String jwtToken;

    @Schema(description = "Is user validated or not", example = "asdasd12321asdasdasd", required = true)
    @NotNull
    private Boolean validated;

}
