/**
 * @author : Oguz Kahraman
 * @since : 19.09.2020
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "suggests", schema = "public", catalog = "sosu_test")
public class Suggest {

    @Id
    @SequenceGenerator(name = "suggests_id_seq", sequenceName = "suggests_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "suggests_id_seq")
    private Long id;

    @Column(name = "suggest")
    private String userSuggest;

    private String lang;

    @Column(name = "user_id")
    private String userName;

    private LocalDateTime date;

}
