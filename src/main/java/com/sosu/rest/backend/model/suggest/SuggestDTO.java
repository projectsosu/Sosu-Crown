/**
 * @author : Oguz Kahraman
 * @since : 19.09.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.model.suggest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SuggestDTO {

    @Schema(description = "Id of suggest", example = "5ee73fdb2ace0520f01af3dc")
    private Long id;

    @Schema(description = "Suggest comment", example = "5ee73fdb2ace0520f01af3dc")
    private String suggest;

    @Schema(description = "Suggest lang", example = "5ee73fdb2ace0520f01af3dc")
    private String lang;

    @Schema(description = "Name of suggestter", example = "5ee73fdb2ace0520f01af3dc")
    private String userName;

    @Schema(description = "Suggest date", example = "2020-10-10")
    private LocalDateTime date;

    @Schema(description = "Like count", example = "1")
    private Long likeCount;

    @Schema(description = "Comment count", example = "1")
    private Long commentCount;

    @Schema(description = "Re suggest count", example = "1")
    private Long reSuggestCount;

}