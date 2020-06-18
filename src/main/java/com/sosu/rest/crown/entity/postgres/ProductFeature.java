package com.sosu.rest.crown.entity.postgres;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
@Data
@NoArgsConstructor
public class ProductFeature {

    @Id
    @SequenceGenerator(name = "product_feature_id_seq", sequenceName = "product_feature_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_feature_id_seq")
    private Long id;

    private String value;

    private String feature;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    public ProductFeature(String value, String feature, Product product) {
        this.value = value;
        this.feature = feature;
        this.product = product;
    }

}
