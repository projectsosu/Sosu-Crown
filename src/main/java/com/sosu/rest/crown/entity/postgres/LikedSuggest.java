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

@Entity
@Data
@Table(name = "liked_suggests", schema = "public", catalog = "sosu_test")
public class LikedSuggest {

    @Id
    @SequenceGenerator(name = "liked_suggests_id_seq", sequenceName = "liked_suggests_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "liked_suggests_id_seq")
    private Long id;

    private Long suggestId;

    @Column(name = "user_id")
    private String userName;

}
