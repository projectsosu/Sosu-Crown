/**
 * @author : Oguz Kahraman
 * @since : 19.09.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.model.suggest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SuggestCommentDTO {

    @Schema(description = "Id of suggest", example = "5ee73fdb2ace0520f01af3dc")
    private Long id;

    @Schema(description = "Suggest comment", example = "5ee73fdb2ace0520f01af3dc")
    private String comment;

    @Schema(description = "Name of suggestter", example = "5ee73fdb2ace0520f01af3dc")
    private String userName;

    @Schema(description = "Suggest date", example = "2020-10-10")
    private LocalDateTime date;

    @Schema(description = "Like count", example = "1")
    private Long likeCount;

    @Schema(description = "Comment count", example = "1")
    private Long commentCount;

    @Schema(description = "Re Suggest count", example = "1")
    private Long reSuggestCount;

}
