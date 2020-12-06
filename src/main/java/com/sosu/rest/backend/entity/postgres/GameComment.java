/**
 * @author : Oguz Kahraman
 * @since : 16.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.entity.postgres;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "game_comment")
@Data
public class GameComment {

    @Id
    @SequenceGenerator(name = "game_comment_id_seq", sequenceName = "game_comment_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game_comment_id_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Game game;

    private String comment;

    private String lang;

    @Column(name = "user_id")
    private String userName;

    @OneToMany
    @JoinColumn(name = "parent_id")
    private List<ProductComment> replies;

    private LocalDateTime date = LocalDateTime.now();

}
