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
import java.util.List;


@Entity
@Data
@TypeDef(
        name = "list-array",
        typeClass = ListArrayType.class
)
public class Product {

    @Id
    @SequenceGenerator(name = "id_sequence", sequenceName = "id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_sequence")
    private Long id;

    private String name;

    private String description;

    private Integer year;

    private String image;

    @Column(name = "category_id")
    private String categoryId;

    @Type(type = "list-array")
    private List<String> categoryIdList;

    @Type(type = "list-array")
    private List<String> mainCategoryIdList;

    @Column(name = "main_category_id")
    private String mainCategoryId;

    private String status;

    private String cert;

    private Integer min;

    @Column(name = "imdb_id")
    private String imdbId;

    private Integer tmdbId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<ProductFeature> productFeatures;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product", orphanRemoval = true)
    private List<ProductComment> productComments;
}
