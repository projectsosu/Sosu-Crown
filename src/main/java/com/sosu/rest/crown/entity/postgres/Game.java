/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.entity.postgres;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;


@Entity
@Data
@Table(name = "games")
public class Game {

    @Id
    @SequenceGenerator(name = "games_id_seq", sequenceName = "games_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "games_id_seq")
    private Long id;

    private String name;

    @Column(name = "category_id")
    private String categoryId;

    @Column(name = "console_category_id")
    private String consoleCategoryId;

    @Column(name = "main_category_id")
    private String mainCategoryId;

    @Column(name = "publish_date")
    private LocalDate publishDate;

    private String publisher;

    private String developer;

    private String description;

    private String cert;

    private String image;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game", orphanRemoval = true)
    private List<GameComment> productComments;

}
