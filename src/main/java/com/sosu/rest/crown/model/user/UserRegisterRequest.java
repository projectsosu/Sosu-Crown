package com.sosu.rest.crown.model.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserRegisterRequest {

    @Schema(description = "Password of user", example = "123456", required = true)
    @NotEmpty
    private String password;

    @Schema(description = "Birth date of user", example = "2000-10-10", required = true)
    @NotEmpty
    private String birthDate;

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

}
