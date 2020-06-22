package com.sosu.rest.crown.entity.postgres;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.List;


@Entity
@Data
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

    @Column(name = "main_category_id")
    private String mainCategoryId;

    private String status;

    private String cert;

    private Integer min;

    @Column(name = "imdb_id")
    private String imdbId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<ProductFeature> productFeatures;
}
