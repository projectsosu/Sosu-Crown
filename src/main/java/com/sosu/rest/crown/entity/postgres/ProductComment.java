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
@Table(name = "product_comment")
@Data
public class ProductComment {

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Id
    private String comment;

    private String lang;

    @Column(name = "user_id")
    private String userName;

}
