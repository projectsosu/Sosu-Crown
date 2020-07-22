package com.sosu.rest.crown.model.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
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

    @Schema(description = "Surname of user", example = "App", required = true)
    @NotEmpty
    private String surname;

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