/**
 * @author : Oguz Kahraman
 * @since : 19.09.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.entity.postgres;

import com.sosu.rest.crown.entity.postgres.embeddedid.UserFollowId;
import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "follow")
@Data
public class UserFollow {

    @EmbeddedId
    private UserFollowId id;

    private LocalDateTime followDate = LocalDateTime.now();

}
