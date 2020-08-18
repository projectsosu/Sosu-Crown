/**
 * @author : Oguz Kahraman
 * @since : 16.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.entity.postgres;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "game_comment")
@Data
public class GameComment {

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Game game;

    @Id
    private String comment;
    private String lang;

    @Column(name = "user_id")
    private String userName;

}
