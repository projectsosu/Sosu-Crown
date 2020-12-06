/**
 * @author : Oguz Kahraman
 * @since : 19.09.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.model.suggest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SuggestCommentRequest {

    @Schema(description = "Suggest comment", example = "5ee73fdb2ace0520f01af3dc")
    private String comment;

    @Schema(description = "Name of suggestter", example = "5ee73fdb2ace0520f01af3dc")
    private String userName;

    @Schema(description = "Parent suggest comment id", example = "5ee73fdb2ace0520f01af3dc")
    private Long parentId;

    @Schema(description = "Suggest Id", example = "5ee73fdb2ace0520f01af3dc")
    private Long suggestId;
}
