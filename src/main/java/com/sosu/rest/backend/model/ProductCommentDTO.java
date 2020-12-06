/**
 * @author : Oguz Kahraman
 * @since : 19.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ProductCommentDTO {

    @Schema(description = "Product comment", example = "5ee73fdb2ace0520f01af3dc")
    private String comment;

    @Schema(description = "Username of user", example = "example")
    private String userName;
    
}
