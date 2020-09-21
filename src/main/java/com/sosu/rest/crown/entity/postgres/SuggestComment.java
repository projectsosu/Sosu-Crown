/**
 * @author : Oguz Kahraman
 * @since : 19.09.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.entity.postgres;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "suggest_comments", schema = "public", catalog = "sosu_test")
public class SuggestComment {

    @Id
    @SequenceGenerator(name = "suggest_comments_id_seq", sequenceName = "suggest_comments_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "suggest_comments_id_seq")
    private Long id;

    @Column(name = "user_id")
    private String userName;

    private Long suggestId;

    private Long parentId;

    private String comment;

    private LocalDateTime date = LocalDateTime.now();

}
