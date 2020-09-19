/**
 * @author : Oguz Kahraman
 * @since : 19.09.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.entity.postgres.embeddedid;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class UserFollowId implements Serializable {

    @Column(name = "user_id")
    private String userName;

    @Column(name = "followed_user_id")
    private String followedUserName;

}
