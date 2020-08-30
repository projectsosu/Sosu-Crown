/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.entity.postgres;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

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
@TypeDef(
        name = "list-array",
        typeClass = ListArrayType.class
)
public class Game {

    @Id
    @SequenceGenerator(name = "games_id_seq", sequenceName = "games_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "games_id_seq")
    private Long id;

    private String name;

    @Type(type = "list-array")
    private List<String> categoryIdList;

    @Type(type = "list-array")
    private List<String> mainCategoryIdList;

    @Type(type = "list-array")
    private List<String> consoleCategoryIdList;

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
